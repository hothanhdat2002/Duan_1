package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.modal.LogoCompany;

import java.util.ArrayList;

public class LogoCompanyAdapter extends RecyclerView.Adapter<LogoCompanyAdapter.LogoCompanyViewHolder>{

    public Context context;
    public ArrayList<LogoCompany> logoCompanyList;

    public LogoCompanyAdapter(Context context){
        this.context = context;
    }
    public void setData(ArrayList<LogoCompany> logoCompanyList){
        this.logoCompanyList = logoCompanyList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LogoCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logocompany,parent,false);
        return new LogoCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogoCompanyViewHolder holder, int position) {
        LogoCompany logoCompany = logoCompanyList.get(position);
        holder.imgLogoCompany.setImageResource(logoCompany.getImage());
    }

    @Override
    public int getItemCount() {
        return logoCompanyList.size();
    }

    public class LogoCompanyViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgLogoCompany;
        public LogoCompanyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLogoCompany = itemView.findViewById(R.id.kawasaki_company);
        }
    }
}
