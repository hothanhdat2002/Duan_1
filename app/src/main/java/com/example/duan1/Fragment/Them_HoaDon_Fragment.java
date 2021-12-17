package com.example.duan1.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.duan1.Adapter.Admin_Item_Adapter;
import com.example.duan1.DAO.HoaDonDAO;
import com.example.duan1.Model.Admin;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Them_HoaDon_Fragment extends DialogFragment implements FragmentResultListener, DatePickerDialog.OnDateSetListener {
    private Button btn_add,btn_cancel;
    private TextView tv_created,tv_createdValue;
    private Admin_Item_Adapter item_adapter;
    private List<Admin> listAdmin;
    private TextInputLayout tl_khachhang;
    private TextInputEditText et_name_customer;
    private Date createdDate = new Date();
    private Integer admin_id_sp;   //ID Admin trong Spinner

    public static Them_HoaDon_Fragment newInstance(Integer id, String name_customer, Long date ){
        Them_HoaDon_Fragment fragment = new Them_HoaDon_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name_customer",name_customer);
        bundle.putLong("date",date);
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
        et_name_customer = (TextInputEditText) view.findViewById(R.id.et_tenkhachhang);
        tl_khachhang = (TextInputLayout) view.findViewById(R.id.tl_khachhang);
        btn_add = (Button) view.findViewById(R.id.btn_add_hoadon);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel_hoadon);
        tv_created = (TextView) view.findViewById(R.id.tv_created);
        tv_createdValue = (TextView) view.findViewById(R.id.tv_createdValue);


        //bắt lỗi nhập tên
        et_name_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>15){
                    tl_khachhang.setError("Error!");
                    tl_khachhang.setEndIconDrawable(R.drawable.error);
                }else{
                    tl_khachhang.setError(null);
                    tl_khachhang.setEndIconDrawable(R.drawable.done);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //lấy dữ liệu từ Argument
        Integer id = getArguments().getInt("id",-1);
        String name_customer = getArguments().getString("name_customer","");
        Long date  = getArguments().getLong("date");




        // nếu id ==-1 là thêm
        if (id==-1){
            et_name_customer.setText("");
        }else{
            et_name_customer.setText(name_customer);
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

        //Nút thêm dữ liệu
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonDAO dao = new HoaDonDAO(getContext());
                String name_customer = et_name_customer.getText().toString();
                if (name_customer.isEmpty()|| name_customer.length()>10){
                    Toast.makeText(getContext(), "Invalid category name", Toast.LENGTH_SHORT).show();
                }else {
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setName_customer(name_customer);
                    hoaDon.setDate(createdDate);
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
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        getDialog().getWindow().setLayout(1000, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    @Override
    public void onFragmentResult( String requestKey,Bundle result) {

    }
}
