package com.example.duan1.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.AdminDAO;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.HoaDonDAO;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.Them_HoaDon_Fragment;
import com.example.duan1.Model.Admin;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class HoaDon_Adapter extends RecyclerView.Adapter<HoaDon_Adapter.ViewHolder>{
    private List<HoaDon> data;
    private Context context;
    public HoaDon_Adapter(List<HoaDon> _data, Context _context){
        this.data = _data;
        this.context = _context;
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
        TextView tvName_Admin = holder.getTv_name_admin();
        Button btn_update = holder.getBtn_update_hoadon();
        Button btn_delete = holder.getBtn_delete_hoadon();

        tvName_Customer.setText(hoaDon.getName_customer());
        //lấy tên admin theo id
        AdminDAO dao = new AdminDAO(context);
        int id_admin = hoaDon.getId_admin();
        Admin admin = dao.getByID(id_admin);
        tvName_Admin.setText(admin.getName());

        //đinh dạng ngày ( Date -> dd - MM - yyyy )
        SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
        tvDate.setText(sdf.format(hoaDon.getDate()));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO dao = new HoaDonDAO(context);
                dao.delete(hoaDon.getId());
                List<HoaDon> _data = dao.get();
                updateList(_data);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                //new Instance Update
                Them_HoaDon_Fragment diaLogFragment = Them_HoaDon_Fragment.newInstance(hoaDon.getId(),
                        hoaDon.getName_customer(),hoaDon.getDate().getTime(), hoaDon.getId_admin() );
                diaLogFragment.show(fragmentManager,"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tv_name_customer;
        private final TextView tv_date;
        private final TextView tv_name_admin;
        private final Button btn_delete_hoadon;
        private final Button btn_update_hoadon;
        public IMyViewHolderClicks mListener_bill;

        public ViewHolder( View itemView,IMyViewHolderClicks _mlistener) {
            super(itemView);
            tv_name_customer = itemView.findViewById(R.id.tv_name_customer);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name_admin = itemView.findViewById(R.id.tv_name_admin);
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

        public TextView getTv_name_admin() {
            return tv_name_admin;
        }

        public Button getBtn_delete_hoadon() {
            return btn_delete_hoadon;
        }

        public Button getBtn_update_hoadon() {
            return btn_update_hoadon;
        }
    }
    public void updateList(List<HoaDon> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
}
