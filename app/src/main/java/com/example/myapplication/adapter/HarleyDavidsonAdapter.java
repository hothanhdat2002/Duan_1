package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.modal.HarleyDavidson;
import com.example.myapplication.modal.LogoCompany;

import java.util.ArrayList;

public class HarleyDavidsonAdapter extends RecyclerView.Adapter<HarleyDavidsonAdapter.HarleyDavidsonViewHolder>{

    public Context context;
    public ArrayList<HarleyDavidson> harleyDavidsonList;

    public HarleyDavidsonAdapter(Context context, ArrayList<HarleyDavidson> harleyDavidsonList){
        this.context = context;
        this.harleyDavidsonList = harleyDavidsonList;
    }

    @NonNull
    @Override
    public HarleyDavidsonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new HarleyDavidsonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarleyDavidsonViewHolder holder, int position) {

        HarleyDavidson harleyDavidson = harleyDavidsonList.get(position);

        holder.background.setImageResource(harleyDavidson.getBackground());
        holder.name.setText(harleyDavidson.getName());
        holder.category.setText(harleyDavidson.getCategory());
        holder.price.setText(harleyDavidson.getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return harleyDavidsonList.size();
    }

    public class HarleyDavidsonViewHolder extends RecyclerView.ViewHolder {
        ImageView background;
        TextView name;
        TextView category;
        TextView price;

        public HarleyDavidsonViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.background);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            price = itemView.findViewById(R.id.price);
        }
    }
}
