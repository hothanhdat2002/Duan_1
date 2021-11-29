package com.example.duan1.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.duan1.Adapter.Admin_Item_Adapter;
import com.example.duan1.Adapter.HangXe_Item_Adapter;
import com.example.duan1.DAO.AdminDAO;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.HoaDonDAO;
import com.example.duan1.Model.Admin;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Them_HoaDon_Fragment extends DialogFragment implements FragmentResultListener, DatePickerDialog.OnDateSetListener {
    private EditText et_name_customer;
    private Button btn_add,btn_cancel;
    private Spinner sp_admin;
    private TextView tv_created,tv_createdValue;
    private Admin_Item_Adapter item_adapter;
    private List<Admin> listAdmin;
    private Date createdDate = new Date();
    private Integer admin_id_sp;   //ID Admin trong Spinner

    public static Them_HoaDon_Fragment newInstance(Integer id, String name_customer, Long date, Integer id_admin ){
        Them_HoaDon_Fragment fragment = new Them_HoaDon_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name_customer",name_customer);
        bundle.putLong("date",date);
        bundle.putInt("id_admin",id_admin);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_them_hoadon_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_name_customer = (EditText) view.findViewById(R.id.et_tenkhachhang);
        btn_add = (Button) view.findViewById(R.id.btn_add_hoadon);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel_hoadon);
        sp_admin = (Spinner) view.findViewById(R.id.sp_admin);
        tv_created = (TextView) view.findViewById(R.id.tv_created);
        tv_createdValue = (TextView) view.findViewById(R.id.tv_createdValue);

        //lấy dữ liệu từ Argument
        Integer id = getArguments().getInt("id",-1);
        String name_customer = getArguments().getString("name_customer","");
        Long date  = getArguments().getLong("date");
        Integer id_admin = getArguments().getInt("id_admin");

        
        //Đổ dữ liệu lên spinner;
        listAdmin = (new AdminDAO(getContext()).get());
        item_adapter = new Admin_Item_Adapter(listAdmin,getContext());
        sp_admin.setAdapter(item_adapter);
        sp_admin.setSelection(0);

        // nếu id ==-1 là thêm
        if (id==-1){
            et_name_customer.setText("");
        }else{
            et_name_customer.setText(name_customer);
            int selectedIndex = getIndex(listAdmin,id_admin);   //lấy dữ liệu từ index đã chọn
            sp_admin.setSelection(selectedIndex);

            SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");      //ngày đã chọn
            tv_createdValue.setText(sdf.format(date));
        }
        //Hiển thị dialog ngày tháng
        tv_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getChildFragmentManager(),"datePicker");
            }
        });

        //Khi click 1 item spinner thì sẽ lấy được ID
        sp_admin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Admin admin = (Admin) parent.getItemAtPosition(position);
                admin_id_sp = admin.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Nút thêm dữ liệu
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonDAO dao = new HoaDonDAO(getContext());
                String name_customer = et_name_customer.getText().toString();

                HoaDon hoaDon = new HoaDon();
                hoaDon.setName_customer(name_customer);
                hoaDon.setDate(createdDate);
                hoaDon.setId_admin(admin_id_sp);
                if (id == -1) {
                    dao.insert(hoaDon);
                }else{
                    hoaDon.setId(id);
                    dao.update(hoaDon);
                }

                //truyền key, bundle và thông báo cho FRAGMENT A là đã kết thúc;
                getParentFragmentManager().setFragmentResult("key", new Bundle());
                dismiss();
            }
        });


        getDialog().getWindow().setLayout(1000, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    //Hàm tạo ngày
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){
        final Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,monthOfYear);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        createdDate = c.getTime();
        tv_createdValue.setText(dayOfMonth + " thg " + (monthOfYear+1) + ", " + year + "");

    }

    //lấy id của spinner đã chọn
    private int getIndex(List<Admin> admin, int _admin_id){
        for (int i = 0; i < admin.size();i++){
            if (admin.get(i).getId() == _admin_id)
                return i;
        }
        return 0;
    }
    @Override
    public void onFragmentResult( String requestKey,Bundle result) {

    }
}
