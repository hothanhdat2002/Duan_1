package com.example.duan1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.Fragment.Detail_Xe_Fragment;
import com.example.duan1.Fragment.HangXe_Fragment;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.Them_HangXe_Fragment;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.util.List;

public class Home_HangXe_Adapter extends RecyclerView.Adapter<Home_HangXe_Adapter.ViewHolder>{
    private List<HangXe> data;

    private Context context;

    public Home_HangXe_Adapter(List<HangXe> _data, Context _context){
        this.data = _data;
        this.context = _context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_home_hangxe,parent,false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.IMyViewHolderClicks() {
            @Override
            public void onItemClick(View caller) {
                Fragment fragment = new HangXe_Fragment();
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Set Fragmentclass Arguments
                fragmentTransaction.replace(R.id.my_frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return holder;
    }
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(Home_HangXe_Adapter.ViewHolder holder, int position) {
        HangXe hangXe = data.get(position);
        ImageView imageViewLogo  = holder.getIv_Logo();

        //chuyển byte thành hình ảnh
        byte[] logo = hangXe.getLogo();
        Bitmap bitmaps = BitmapFactory.decodeByteArray(logo,0,logo.length);
        imageViewLogo.setImageBitmap(bitmaps);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView iv_Logo;
        public IMyViewHolderClicks mListener_bill;
        public ViewHolder(View itemView,IMyViewHolderClicks _mlistener) {
            super(itemView);
            iv_Logo = itemView.findViewById(R.id.iv_logo_home);

            mListener_bill = _mlistener;
            itemView.setOnClickListener(this);
        }
        public ImageView getIv_Logo() {
            return iv_Logo;
        }

        @Override
        public void onClick(View v) {
            mListener_bill.onItemClick(v);
        }
        public static interface IMyViewHolderClicks{
            public void onItemClick(View caller);
        }
    }

}
