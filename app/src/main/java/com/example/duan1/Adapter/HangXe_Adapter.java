package com.example.duan1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Fragment.Them_HangXe_Fragment;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class HangXe_Adapter extends RecyclerView.Adapter<HangXe_Adapter.ViewHolder> implements Filterable {
    private List<HangXe> data;
    private List<Xe> listXe;
    private Context context;
    List<HangXe> arrSortHangXe;
    private Filter HangXeFilter;
    public HangXe_Adapter(List<HangXe> _data, Context _context){
        this.data = _data;
        this.context = _context;
        this.arrSortHangXe = data;
    }
    @Override
    public HangXe_Adapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hangxe,parent,false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.IMyViewHolderLongClicks(){
            @Override
            public void onItemLongClick(View caller) {
                PopupMenu menu = new PopupMenu(parent.getContext(),caller);
                menu.inflate(R.menu.menu_pop_up);
                menu.setGravity(Gravity.END);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        HangXe hangXe = data.get(getItemViewType(viewType));
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                                //newInstance update
                                Them_HangXe_Fragment diaLogFragment = Them_HangXe_Fragment.newInstance(hangXe.getId(),hangXe.getName(),hangXe.getLogo());
                                diaLogFragment.show(fragmentManager,"");
                                return true;
                            case R.id.menu_delete:
                                HangXeDAO dao = new HangXeDAO(context);
                                XeDAO xeDAO = new XeDAO(context);
                                listXe = xeDAO.get();
                                for (int i =0;i<listXe.size();i++){
                                    if (listXe.get(i).getIdhangxe()==hangXe.getId()){
                                        Toast.makeText(parent.getContext(), "Error! An error occurred. Please try again later", Toast.LENGTH_SHORT).show();
                                    }else {
                                        dao.delete(hangXe.getId());
                                        List<HangXe> _data = dao.get();
                                        updateList(_data);
                                    }
                                }

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                menu.show();
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
    public void onBindViewHolder( HangXe_Adapter.ViewHolder holder, int position) {
        HangXe hangXe = data.get(position);
        TextView textViewName = holder.getTv_Name();
        ImageView imageViewLogo  = holder.getIv_Logo();

        textViewName.setText(hangXe.getName());
        //chuyển byte thành hình ảnh
        byte[] logo = hangXe.getLogo();
        Bitmap bitmaps = BitmapFactory.decodeByteArray(logo,0,logo.length);
        imageViewLogo.setImageBitmap(bitmaps);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        if (HangXeFilter == null)
            HangXeFilter = new CustomFilter();
        return HangXeFilter;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private final TextView tv_Name;
        private final ImageView iv_Logo;

        public IMyViewHolderLongClicks mListener_member;
        public ViewHolder( View itemView,IMyViewHolderLongClicks _mListener) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_name_hangxe);
            iv_Logo = itemView.findViewById(R.id.iv_logo);

            mListener_member = _mListener;
            itemView.setOnLongClickListener(this);
        }

        public TextView getTv_Name() {
            return tv_Name;
        }

        public ImageView getIv_Logo() {
            return iv_Logo;
        }

        //tạo hàm longClick
        public boolean onLongClick(View view){
            mListener_member.onItemLongClick(view);
            return true;
        }

        public static interface IMyViewHolderLongClicks{
            public void onItemLongClick(View caller);
        }

    }
    public void updateList(List<HangXe> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    public void resetData() {
        data = arrSortHangXe;
    }
    public void changeDataset(ArrayList<HangXe> items){
        this.data = items;
        notifyDataSetChanged();
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortHangXe;
                results.count = arrSortHangXe.size();
            }else{
                List<HangXe> lsHang = new ArrayList<HangXe>();
                for (HangXe p : data) {
                    if
                    (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsHang.add(p);
                }
                results.values = lsHang;
                results.count = lsHang.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count == 0)
                notifyDataSetChanged();
            else {
                data = (List<HangXe>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }

}
