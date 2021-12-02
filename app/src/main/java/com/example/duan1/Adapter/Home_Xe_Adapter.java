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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Fragment.Detail_Xe_Fragment;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.Them_Xe_Fragment;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.util.List;

public class Home_Xe_Adapter extends RecyclerView.Adapter<Home_Xe_Adapter.ViewHolder>{
    private List<Xe> data;
    private Context context;
    public Home_Xe_Adapter(List<Xe> _data, Context _context){
        this.data = _data;
        this.context = _context;
    }
    @Override
    public Home_Xe_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_home_xe,parent,false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.IMyViewHolderClicks() {
            @Override
            public void onItemClick(View caller) {
                Fragment fragment = new Detail_Xe_Fragment();
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                Xe xe = data.get(getItemViewType(viewType));
                int xeId = xe.getId();
                bundle.putInt("id_xe",xeId);

                // Set Fragmentclass Arguments
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.my_frame_layout, fragment);
                fragmentTransaction.addToBackStack(HoaDonChiTiet_Fragment.TAG);
                fragmentTransaction.commit();

            }
        });
        return holder;
    }
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(Home_Xe_Adapter.ViewHolder holder, int position) {
        Xe xe = data.get(position);
        TextView textViewName = holder.getTv_Name();
        TextView textViewPrice = holder.getTv_Price();
        TextView textViewAmount = holder.getTv_Amount();
        ImageView imageViewXe  = holder.getIv_Xe();
        ImageView imageViewLogo  = holder.getIv_Logo();


        textViewName.setText(xe.getName());
        textViewName.setTextColor(xe.getColor());
        textViewPrice.setText("Rp. "+xe.getPrice());
        textViewAmount.setText("x"+xe.getAmount());

        //chuyển byte thành hình ảnh
        byte[] images = xe.getImages();
        Bitmap bitmaps = BitmapFactory.decodeByteArray(images,0,images.length);
        imageViewXe.setImageBitmap(bitmaps);

        //lấy ảnh logo từ bảng hãng xe theo id
        HangXeDAO hangXeDAO = new HangXeDAO(context);
        int id_hangxe = xe.getIdhangxe();
        HangXe hangXe = hangXeDAO.getByID(id_hangxe);

        byte[] logo = hangXe.getLogo();
        Bitmap bitmaps1 = BitmapFactory.decodeByteArray(logo,0,logo.length);
        imageViewLogo.setImageBitmap(bitmaps1);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tv_Name;
        private final TextView tv_Price;
        private final TextView tv_Amount;
        private final ImageView iv_Xe;
        private final ImageView iv_Logo;
        public IMyViewHolderClicks mListener_bill;
        public ViewHolder(View itemView,IMyViewHolderClicks _mlistener) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_name_xe_home);
            tv_Price = itemView.findViewById(R.id.tv_price_home);
            tv_Amount = itemView.findViewById(R.id.tv_amount_home);
            iv_Xe = itemView.findViewById(R.id.iv_xe_home);
            iv_Logo = itemView.findViewById(R.id.iv_hangxe_home);
            mListener_bill = _mlistener;
            itemView.setOnClickListener(this);
        }
        public TextView getTv_Name() {
            return tv_Name;
        }

        public TextView getTv_Price() {
            return tv_Price;
        }

        public TextView getTv_Amount() {
            return tv_Amount;
        }

        public ImageView getIv_Xe() {
            return iv_Xe;
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
