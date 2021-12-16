package com.example.duan1.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

public class Them_HangXe_Fragment extends DialogFragment implements FragmentResultListener {
    private Button btn_add,btn_delete,btn_choose;
    private ImageView iv_hangxe;
    private TextInputLayout tl_hangxe;
    private TextInputEditText et_name_hangxe;
    public static Them_HangXe_Fragment newInstance(Integer id, String name,byte[] logo){
        Them_HangXe_Fragment fragment = new Them_HangXe_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name",name);
        bundle.putByteArray("logo",logo);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_them_hangxe_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_name_hangxe = (TextInputEditText) view.findViewById(R.id.et_ten_hangxe);
        btn_add = (Button) view.findViewById(R.id.btn_add_hangxe);
        iv_hangxe = (ImageView) view.findViewById(R.id.iv_dialog_hangxe);
        btn_delete = (Button) view.findViewById(R.id.btn_cancel_hangxe);
        btn_choose = (Button) view.findViewById(R.id.btn_choose_hangxe);
        tl_hangxe = (TextInputLayout) view.findViewById(R.id.tl_hangxe);
        et_name_hangxe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>10){
                    tl_hangxe.setError("Error!");
                    tl_hangxe.setEndIconDrawable(R.drawable.error);
                }else{
                    tl_hangxe.setError(null);
                    tl_hangxe.setEndIconDrawable(R.drawable.done);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //nút chọn ảnh
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // xin quyền truy cập
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},999);
                    }else {
                        //hiển thị thư viện
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 2);
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        //lấy dữ liệu từ Argument
        Integer id = getArguments().getInt("id",-1);
        String name = getArguments().getString("name","");
        byte[]logo = getArguments().getByteArray("logo");

        // nếu id ==-1 là thêm
        if (id==-1){
            et_name_hangxe.setText("");
            iv_hangxe.setImageResource(R.drawable.ducati_logo);
        }else{                                                       // ngược lại là sửa
            et_name_hangxe.setText(name);
            iv_hangxe.setImageBitmap(ByteToImageView(logo));

        }

        //nhấn nút thêm
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HangXeDAO dao = new HangXeDAO(getContext());
                    String name = et_name_hangxe.getText().toString();
                    byte[] logo = imageViewToByte(iv_hangxe);
                    if (name.isEmpty()|| name.length()>10){
                        Toast.makeText(getContext(), "Invalid category name", Toast.LENGTH_SHORT).show();
                    }else {
                        HangXe hangXe = new HangXe();
                        hangXe.setName(name);
                        hangXe.setLogo(logo);

                        // xét ID xem dialog là thêm hay sửa
                        if (id == -1) {
                            dao.insert(hangXe);
                        } else {
                            hangXe.setId(id);
                            dao.update(hangXe);
                        }

                        //truyền key, bundle và thông báo cho FRAGMENT A là đã kết thúc;
                        getParentFragmentManager().setFragmentResult("key", new Bundle());
                        dismiss();
                    }
                }
            });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDialog().getWindow().setLayout(1000, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
            iv_hangxe.setImageURI(selectedImage);
        }
    }
    @Override
    public void onFragmentResult(String requestKey, Bundle result) {

    }
}
