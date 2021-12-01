package com.example.duan1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.duan1.Model.Top;
import com.example.duan1.R;

import java.util.List;

public class Top10_Adapter extends RecyclerView.Adapter<Top10_Adapter.ViewHolder>{
    private List<Top> data;
    private Context context;

    public Top10_Adapter(List<Top> data, Context context) {
        this.data = data;
        this.context = context;
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
}
