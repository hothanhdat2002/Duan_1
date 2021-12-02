package com.example.duan1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Fragment.Detail_Xe_Fragment;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.Them_HangXe_Fragment;
import com.example.duan1.Fragment.Them_Xe_Fragment;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class Xe_Adapter extends RecyclerView.Adapter<Xe_Adapter.ViewHolder> implements Filterable {
    private List<Xe> data;
    private Context context;
    List<Xe> arrSortXe;
    private Filter XeFilter;
    public Xe_Adapter(List<Xe> _data, Context _context){
        this.data = _data;
        this.context = _context;
        this.arrSortXe = data;
    }
    @Override
    public Xe_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_xe,parent,false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.IMyViewHolderLongClicks() {
            @Override
            public void onItemLongClick(View caller) {
                PopupMenu menu = new PopupMenu(parent.getContext(),caller);
                menu.inflate(R.menu.menu_pop_up);
                menu.setGravity(Gravity.END);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Xe xe = data.get(getItemViewType(viewType));
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                                //newInstance update
                                Them_Xe_Fragment diaLogFragment = Them_Xe_Fragment.newInstance(xe.getId(),xe.getName(),xe.getImages(),xe.getColor(),xe.getAmount(),xe.getPrice(),
                                        xe.getMass(),xe.getSpeed(),xe.getFuel(),xe.getVolume(),xe.getEngine(),xe.getIdhangxe());
                                diaLogFragment.show(fragmentManager,"");
                                return true;
                            case R.id.menu_delete:
                                XeDAO dao = new XeDAO(context);
                                dao.delete(xe.getId());
                                List<Xe> _data = dao.get();
                                updateList(_data);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                menu.show();
            }
        }, new ViewHolder.IMyViewHolderClicks(){
            @Override
            public void onItemClick(View caller) {
                Fragment fragment = new Detail_Xe_Fragment();
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                Xe xe = data.get(getItemViewType(viewType));
                int id_xe = xe.getId();
                bundle.putInt("id_xe", id_xe);

                // Set Fragmentclass Arguments
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.my_frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });
        return holder;
    }
    //hàm lấy index khi longClick
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder( Xe_Adapter.ViewHolder holder, int position) {
        Xe xe = data.get(position);
        TextView textViewName = holder.getTv_Name();
        TextView textViewPrice = holder.getTv_Price();
        TextView textViewAmount = holder.getTv_Amount();
        ImageView imageViewXe  = holder.getIv_Xe();
        ImageView imageViewLogo  = holder.getIv_Logo();


        textViewName.setText(xe.getName());
        textViewName.setTextColor(xe.getColor());
        textViewPrice.setText("Rp. "+xe.getPrice());
        textViewAmount.setText("Amount: "+xe.getAmount());

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

    @Override
    public Filter getFilter() {
        if (XeFilter == null)
            XeFilter = new Xe_Adapter.CustomFilter();
        return XeFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        private final TextView tv_Name;
        private final TextView tv_Price;
        private final TextView tv_Amount;
        private final ImageView iv_Xe;
        private final ImageView iv_Logo;

        public IMyViewHolderLongClicks mListener_member;
        public IMyViewHolderClicks mListener;
        public ViewHolder(View itemView, IMyViewHolderLongClicks _mListener, IMyViewHolderClicks _mListener1) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_name_xe);
            tv_Price = itemView.findViewById(R.id.tv_price);
            tv_Amount = itemView.findViewById(R.id.tv_amount);
            iv_Xe = itemView.findViewById(R.id.iv_xe);
            iv_Logo = itemView.findViewById(R.id.iv_hangxe);

            mListener =  _mListener1;
            mListener_member = _mListener;
            itemView.setOnLongClickListener(this);
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
        public boolean onLongClick(View view) {
            mListener_member.onItemLongClick(view);
            return true;
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view);
        }

        public static interface IMyViewHolderLongClicks{
            public void onItemLongClick(View caller);
        }

        public static interface IMyViewHolderClicks{
            public void onItemClick(View caller);
        }
    }
    public void updateList(List<Xe> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    public void resetData() {
        data = arrSortXe;
    }
    public void changeDataset(ArrayList<Xe> items){
        this.data = items;
        notifyDataSetChanged();
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortXe;
                results.count = arrSortXe.size();
            }else{
                List<Xe> lsXe = new ArrayList<Xe>();
                for (Xe p : data) {
                    if
                    (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsXe.add(p);
                }
                results.values = lsXe;
                results.count = lsXe.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count == 0)
                notifyDataSetChanged();
            else {
                data = (List<Xe>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }
}
