package com.example.duan1.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.duan1.DAO.StatisticDAO;
import com.example.duan1.R;

import java.util.Calendar;
import java.util.Date;

public class Revenue_Fragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private TextView tv_createdStart,tv_createdValueStart,tv_createdEnd,tv_createdValueEnd,tv_revenue;
    private Button btn_search;

    private Date createdDate = new Date();
    private Date createdDate1 = new Date();
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_revenue,container,false);
        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        tv_createdStart = view.findViewById(R.id.tv_createdStart);
        tv_createdValueStart = view.findViewById(R.id.tv_createdValueStart);
        tv_createdEnd = view.findViewById(R.id.tv_createdEnd);
        tv_createdValueEnd = view.findViewById(R.id.tv_createdValueEnd);
        btn_search = view.findViewById(R.id.btn_search);
        tv_revenue =view.findViewById(R.id.tv_revenue);
        //ngày bắt đầu
        tv_createdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getChildFragmentManager(),"datePicker");
            }
        });
        Calendar c = Calendar.getInstance();
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH);
         int day = c.get(Calendar.DAY_OF_MONTH);
        //ngày kết thúc
        tv_createdEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),setListener,year,month,day);
                datePickerDialog.show();

            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                createdDate1 = c.getTime();
                month = month +1;
                String date = dayOfMonth +" thg "+month + ", "+ year+"";
                tv_createdValueEnd.setText(date);

            }
        };
        //tìm
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticDAO dao = new StatisticDAO(getContext());
              Double revenue =  dao.getRevenue(createdDate.getTime(),createdDate1.getTime());
                tv_revenue.setText(revenue+"");
            }
        });

    }




    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        createdDate = c.getTime();
        tv_createdValueStart.setText(dayOfMonth + " thg " + (month + 1) + ", " + year + "");


    }

}
