package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.adapter.LogoCompanyAdapter;
import com.example.myapplication.modal.LogoCompany;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView rcvLogoCompany;
    public LogoCompanyAdapter logoCompanyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvLogoCompany = findViewById(R.id.rcvLogoCompany);
        logoCompanyAdapter = new LogoCompanyAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvLogoCompany.setLayoutManager(linearLayoutManager);

        logoCompanyAdapter.setData(getListLogoCompany());
        rcvLogoCompany.setAdapter(logoCompanyAdapter);
    }
    public ArrayList<LogoCompany> getListLogoCompany(){
        ArrayList<LogoCompany> list = new ArrayList<>();

        list.add(new LogoCompany(R.drawable.kawasaki_logo));
        list.add(new LogoCompany(R.drawable.harley_logo));
        list.add(new LogoCompany(R.drawable.yamaha_logo));
        list.add(new LogoCompany(R.drawable.ducati_logo));
        list.add(new LogoCompany(R.drawable.honda_logo));
        return list;
    }
}