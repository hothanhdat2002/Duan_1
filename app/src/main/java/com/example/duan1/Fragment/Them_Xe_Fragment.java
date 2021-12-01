package com.example.duan1.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.duan1.Adapter.HangXe_Item_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class Them_Xe_Fragment extends DialogFragment implements FragmentResultListener {
    Button btn_choose_xe,btn_add,btn_cancel;
    ImageButton ib_giam,ib_tang,btn_choose_color;
    ImageView iv_xe;
    EditText et_tenxe,et_giaxe,et_khoiluong,et_vantoc,et_thetich,et_nhienlieu,et_dongco;
    TextView tv_value_amount;
    LinearLayout layout_color;  // view để hiển thị màu
    List<HangXe> listHang;
    Spinner sp_hangxe;
    private HangXe_Item_Adapter item_adapter;
    int count = 1;
    int data_color = -16777216; // mặc định màu xe là: đen
    private Integer hangxe_id;
    public static Them_Xe_Fragment newInstance(Integer id, String name,byte[] images,int color,
                                               int amount, double price, double mass, double speed, double fuel, int volume, String engine, int idhangxe){
        Them_Xe_Fragment fragment = new Them_Xe_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name",name);
        bundle.putByteArray("images",images);
        bundle.putInt("color",color);
        bundle.putInt("amount",amount);
        bundle.putDouble("price",price);
        bundle.putDouble("mass",mass);
        bundle.putDouble("speed",speed);
        bundle.putDouble("fuel",fuel);
        bundle.putInt("volume",volume);
        bundle.putString("engine",engine);
        bundle.putInt("idhangxe",idhangxe);

        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_them_xe_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_choose_color = view.findViewById(R.id.btn_choose_color);
        btn_choose_xe = view.findViewById(R.id.btn_choose_xe);
        layout_color = view.findViewById(R.id.layout_color);
        ib_giam = view.findViewById(R.id.ib_giam);
        ib_tang = view.findViewById(R.id.ib_tang);
        tv_value_amount = view.findViewById(R.id.tv_value_amount);
        iv_xe = view.findViewById(R.id.iv_dialog_xe);
        sp_hangxe = view.findViewById(R.id.sp_hangxe);
        btn_add = view.findViewById(R.id.btn_add_xe);
        btn_cancel = view.findViewById(R.id.btn_cancel_xe);
        et_tenxe = view.findViewById(R.id.et_tenxe);
        et_giaxe = view.findViewById(R.id.et_giaxe);
        et_khoiluong = view.findViewById(R.id.et_khoiluong);
        et_vantoc = view.findViewById(R.id.et_vantoc);
        et_thetich = view.findViewById(R.id.et_thetich);
        et_nhienlieu = view.findViewById(R.id.et_nhienlieu);
        et_dongco = view.findViewById(R.id.et_dongco);

        //đổ dữ liệu lên spinner;
        listHang = (new HangXeDAO(getContext()).get());
        item_adapter = new HangXe_Item_Adapter(listHang,getContext());
        sp_hangxe.setAdapter(item_adapter);
        sp_hangxe.setSelection(0);

        //nút chọn hình xe
        btn_choose_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // xin quyền truy cập
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},999);
                    }else {
                        //hiển thị thư viện
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        //nút chọn màu
        btn_choose_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiaLogColor();
            }
        });


        //lấy dữ liệu từ Argument
        Integer id = getArguments().getInt("id",-1);
        String name = getArguments().getString("name","");
        byte[]images = getArguments().getByteArray("images");
        Integer color = getArguments().getInt("color",-1);
        Integer amount = getArguments().getInt("amount",-1);
        Double price = getArguments().getDouble("price",0);
        Double mass = getArguments().getDouble("mass",0);
        Double speed = getArguments().getDouble("speed",0);
        Double fuel = getArguments().getDouble("fuel",0);
        Integer volume = getArguments().getInt("volume",-1);
        String engine = getArguments().getString("engine","");
        Integer idhangxe = getArguments().getInt("idhangxe",-1);

        //nút tăng giảm số lượng
        ib_tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });
        ib_giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });
        if (id==-1){
            et_tenxe.setText("");
            et_giaxe.setText("");
            et_dongco.setText("");
            et_khoiluong.setText("");
            et_thetich.setText("");
            et_nhienlieu.setText("");
            et_vantoc.setText("");
            iv_xe.setImageResource(R.drawable.kawasaki);
        }else{
            et_tenxe.setText(name);
            et_giaxe.setText(price+"");
            et_dongco.setText(engine);
            et_khoiluong.setText(mass+"");
            et_thetich.setText(volume+"");
            et_nhienlieu.setText(fuel+"");
            et_vantoc.setText(speed+"");
            data_color = color;
            iv_xe.setImageBitmap(ByteToImageView(images));
            layout_color.setBackgroundColor(data_color);

            tv_value_amount.setText(amount+"");
            int selectedIndex = getIndex(listHang,idhangxe);   //lấy dữ liệu từ index đã chọn
            sp_hangxe.setSelection(selectedIndex);
        }
        //nút thêm xe
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XeDAO dao = new XeDAO(getContext());
                String name = et_tenxe.getText().toString();
                String price = et_giaxe.getText().toString();
                String amount = tv_value_amount.getText().toString();
                String mass = et_khoiluong.getText().toString();
                String speed = et_khoiluong.getText().toString();
                String volume = et_thetich.getText().toString();
                String fuel = et_nhienlieu.getText().toString();
                String engine = et_dongco.getText().toString();
                byte[] images = imageViewToByte(iv_xe);

                Xe xe = new Xe();
                xe.setName(name);
                xe.setImages(images);
                xe.setColor(data_color);
                xe.setAmount(Integer.parseInt(amount));
                xe.setPrice(Double.parseDouble(price));
                xe.setMass(Double.parseDouble(mass));
                xe.setSpeed(Double.parseDouble(speed));
                xe.setVolume(Integer.parseInt(volume));
                xe.setFuel(Double.parseDouble(fuel));
                xe.setEngine(engine);
                xe.setIdhangxe(hangxe_id);


                // xét ID xem dialog là thêm hay sửa
                if (id == -1) {
                    dao.insert(xe);
                }else{
                    xe.setId(id);
                    dao.update(xe);
                }

                //truyền key, bundle và thông báo cho FRAGMENT A là đã kết thúc;
                getParentFragmentManager().setFragmentResult("key", new Bundle());
                dismiss();
            }
        });
        //khi click 1 item spinner thì sẽ lấy được ID
        sp_hangxe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HangXe hangXe = (HangXe) parent.getItemAtPosition(position);
                hangxe_id = hangXe.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getDialog().getWindow().setLayout(1000, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    //hàm mở hộp thoại màu sẵc
    public void openDiaLogColor(){
        final ColorPicker colorPicker = new ColorPicker(getActivity());
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#FFFFFF");
        colors.add("#000000");
        colors.add("#FA3F3E"); // màu đỏ
        colors.add("#7689FF");  //xanh dương
        colors.add("#DC6368");  // màu hồng
        colors.add("#FFC125");  //màu vàng
        colors.add("#C4C4C4");  //xám
        colors.add("#ED9E66");  // màu cam
        colors.add("#8FFF7B"); // màu xanh lục
        colorPicker.setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setDefaultColorButton(Color.parseColor("#000000"))
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        if (color!=0) {
                            layout_color.setBackgroundColor(color);
                            data_color = color;
                        }else {
                            data_color = -16777216;  // default là màu đen
                            layout_color.setBackgroundColor(data_color);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();

    }
    //nút tăng giảm số lượng
    private void increment(){
        String currentValue = tv_value_amount.getText().toString();
        count = Integer.parseInt(currentValue);
        count++;
        tv_value_amount.setText("" + count);

    }
    private void decrement(){
        String currentValue = tv_value_amount.getText().toString();
        count = Integer.parseInt(currentValue);
        if (count<=1){
            count =1;
        }else {
            count--;
            tv_value_amount.setText("" + count);
        }
    }
    //chuyển kiểu dl hình ảnh sang byte
    public byte[] imageViewToByte(ImageView img){
        Bitmap bitmap =((BitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    // chuyển kiểu dl byte sang hình ảnh
    public Bitmap ByteToImageView (byte[] _byte){
        Bitmap bitmap = BitmapFactory.decodeByteArray(_byte, 0, _byte.length);
        return bitmap;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK){
            Uri selectedImage = imageReturnedIntent.getData();
            iv_xe.setImageURI(selectedImage);
        }
    }
    //lấy id của spinner đã chọn
    private int getIndex(List<HangXe> hangXe, int _hangxe_id){
        for (int i = 0; i < hangXe.size();i++){
            if (hangXe.get(i).getId() == _hangxe_id)
                return i;
        }
        return 0;
    }
    @Override
    public void onFragmentResult( String requestKey,  Bundle result) {

    }
}
