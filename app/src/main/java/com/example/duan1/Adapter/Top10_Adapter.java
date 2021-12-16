package com.example.duan1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Top;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class Top10_Adapter extends RecyclerView.Adapter<Top10_Adapter.ViewHolder> implements Filterable {
    private List<Top> data;
    private Context context;
    List<Top> arrSortTop;
    private Filter TopFilter;

    public Top10_Adapter(List<Top> _data, Context context) {
        this.data = _data;
        this.context = context;
        this.arrSortTop = data;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_top10,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Top top = data.get(position);
        TextView tv_id = holder.getTv_id();
        TextView tv_amount = holder.getTv_amount();
        ImageView iv_book = holder.getIv_booktop();

        tv_id.setText(top.getName()+"");
        tv_amount.setText("Amount: "+top.getAmount()+"");

        byte[] topImages = top.getImages();
        Bitmap bitmaps = BitmapFactory.decodeByteArray(topImages,0,topImages.length);
        iv_book.setImageBitmap(bitmaps);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        if (TopFilter == null)
            TopFilter = new Top10_Adapter.CustomFilter();
        return TopFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_id;
        private final TextView tv_amount;
        private final ImageView iv_booktop;
        public ViewHolder( View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_name_top10);
            tv_amount = itemView.findViewById(R.id.tv_amount_top10);
            iv_booktop = itemView.findViewById(R.id.iv_top10);
        }

        public TextView getTv_id() {
            return tv_id;
        }

        public TextView getTv_amount() {
            return tv_amount;
        }

        public ImageView getIv_booktop() {
            return iv_booktop;
        }
    }
    public void resetData() {
        data = arrSortTop;
    }
    public void changeDataset(ArrayList<Top> items){
        this.data = items;
        notifyDataSetChanged();
    }
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortTop;
                results.count = arrSortTop.size();
            }else{
                List<Top> top = new ArrayList<Top>();
                for (Top p : data) {
                    if
                    (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        top.add(p);
                }
                results.values = top;
                results.count = top.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count == 0)
                notifyDataSetChanged();
            else {
                data = (List<Top>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }
}
