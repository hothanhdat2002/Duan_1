package com.example.duan1.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HoaDonChiTietDAO;
import com.example.duan1.DAO.HoaDonDAO;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.Them_HoaDon_Fragment;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.HoaDonChiTiet;
import com.example.duan1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_Adapter extends RecyclerView.Adapter<HoaDon_Adapter.ViewHolder> implements Filterable {
    private List<HoaDon> data;
    private List<HoaDonChiTiet> listHDCT;
    private Context context;
    List<HoaDon> arrSortHoaDon;
    private Filter HoaDonFilter;
    public HoaDon_Adapter(List<HoaDon> _data, Context _context){
        this.data = _data;
        this.context = _context;
        this.arrSortHoaDon = data;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hoadon,parent,false);
        ViewHolder holder = new ViewHolder(view, new ViewHolder.IMyViewHolderClicks() {
            @Override
            public void onItemClick(View caller) {
                Fragment fragment = new HoaDonChiTiet_Fragment();
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                HoaDon hoaDon = data.get(getItemViewType(viewType));
                int id_hoadon = hoaDon.getId();
                bundle.putInt("id_hoadon", id_hoadon);

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
    public void onBindViewHolder( ViewHolder holder, int position) {
        HoaDon hoaDon = data.get(position);
        TextView tvName_Customer = holder.getTv_name_customer();
        TextView tvDate = holder.getTv_date();

        ImageButton btn_update = holder.getBtn_update_hoadon();
        ImageButton btn_delete = holder.getBtn_delete_hoadon();

        tvName_Customer.setText(hoaDon.getName_customer());

        //đinh dạng ngày ( Date -> dd - MM - yyyy )
        SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
        tvDate.setText(sdf.format(hoaDon.getDate()));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO dao = new HoaDonDAO(context);
                HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
                listHDCT = hoaDonChiTietDAO.get();
                for (int i =0;i<listHDCT.size();i++) {
                    if (listHDCT.get(i).getId_hoadon() == hoaDon.getId()) {
                        Toast.makeText(context.getApplicationContext(), "Error! An error occurred. Please try again later", Toast.LENGTH_SHORT).show();
                    }else {
                        dao.delete(hoaDon.getId());
                        List<HoaDon> _data = dao.get();
                        updateList(_data);
                    }
                }

            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                //new Instance Update
                Them_HoaDon_Fragment diaLogFragment = Them_HoaDon_Fragment.newInstance(hoaDon.getId(),
                        hoaDon.getName_customer(),hoaDon.getDate().getTime());
                diaLogFragment.show(fragmentManager,"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        if (HoaDonFilter == null)
            HoaDonFilter = new HoaDon_Adapter.CustomFilter();
        return HoaDonFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tv_name_customer;
        private final TextView tv_date;

        private final ImageButton btn_delete_hoadon;
        private final ImageButton btn_update_hoadon;
        public IMyViewHolderClicks mListener_bill;

        public ViewHolder( View itemView,IMyViewHolderClicks _mlistener) {
            super(itemView);
            tv_name_customer = itemView.findViewById(R.id.tv_name_customer);
            tv_date = itemView.findViewById(R.id.tv_date);

            btn_delete_hoadon = itemView.findViewById(R.id.btn_delete_hoadon);
            btn_update_hoadon = itemView.findViewById(R.id.btn_update_hoadon);

            mListener_bill = _mlistener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            mListener_bill.onItemClick(v);
        }
        public static interface IMyViewHolderClicks{
            public void onItemClick(View caller);
        }

        public TextView getTv_name_customer() {
            return tv_name_customer;
        }

        public TextView getTv_date() {
            return tv_date;
        }


        public ImageButton getBtn_delete_hoadon() {
            return btn_delete_hoadon;
        }

        public ImageButton getBtn_update_hoadon() {
            return btn_update_hoadon;
        }
    }
    public void updateList(List<HoaDon> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
    public void resetData() {
        data = arrSortHoaDon;
    }
    public void changeDataset(ArrayList<HoaDon> items){
        this.data = items;
        notifyDataSetChanged();
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortHoaDon;
                results.count = arrSortHoaDon.size();
            }else{
                List<HoaDon> lsHang = new ArrayList<HoaDon>();
                for (HoaDon p : data) {
                    if
                    (p.getName_customer().toUpperCase().startsWith(constraint.toString().toUpperCase()))
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
                data = (List<HoaDon>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }
}
