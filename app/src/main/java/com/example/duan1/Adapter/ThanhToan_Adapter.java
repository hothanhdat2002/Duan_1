package com.example.duan1.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HoaDonChiTietDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDonChiTiet;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class ThanhToan_Adapter extends RecyclerView.Adapter<ThanhToan_Adapter.ViewHolder>{
    ArrayList<HoaDonChiTiet> data;
    private Context context;

    public ThanhToan_Adapter(ArrayList<HoaDonChiTiet> _data, Context _context){
        this.data = _data;
        this.context = _context;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thanhtoan,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(ThanhToan_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HoaDonChiTiet hoaDonChiTiet = data.get(position);
        TextView tvNameXe = holder.getTv_name_xe();
        TextView tvAmount = holder.getTv_amount();
        ImageView ivXe = holder.getIv_xe();
        TextView tvPrice = holder.getTv_price();
        TextView tvTotal = holder.getTv_total();
        ImageButton ib_delete = holder.getIb_delete();

        //lấy ảnh,tên,giá từ bảng xe theo id
        XeDAO dao = new XeDAO(context);
        int id_xe = hoaDonChiTiet.getId_xe();
        Xe xe = dao.getByID(id_xe);
        //ảnh xe
        byte[] images = xe.getImages();
        Bitmap bitmaps1 = BitmapFactory.decodeByteArray(images,0,images.length);
        ivXe.setImageBitmap(bitmaps1);
        //Tên xe
        String name = xe.getName();
        tvNameXe.setText(name);
        //Giá xe
        Double price = xe.getPrice();
        tvPrice.setText("Rp. "+price);
        Integer sl = hoaDonChiTiet.getAmount();
        tvTotal.setText(""+price * sl);
        tvAmount.setText("x"+sl);


        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonChiTietDAO dao = new HoaDonChiTietDAO(context);
                for (int i = 0;i< data.size();i++){
                    if (data.get(i).getId_xe()==hoaDonChiTiet.getId_xe()) {
                        dao.deleteByID(hoaDonChiTiet.getId_xe());
                    }
                }
                List<HoaDonChiTiet> _data = dao.getByIDSum(hoaDonChiTiet.getId_hoadon());
                updateList(_data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_xe;
        private final TextView tv_name_xe;
        private final TextView tv_amount;
        private final TextView tv_price;
        private final TextView tv_total;
        private ImageButton  ib_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_xe = itemView.findViewById(R.id.iv_hdct_anhxe);
            tv_name_xe = itemView.findViewById(R.id.tv_hdct_name_xe);
            tv_amount = itemView.findViewById(R.id.tv_hdct_amount);
            tv_price = itemView.findViewById(R.id.tv_hdct_gia);
            tv_total = itemView.findViewById(R.id.tv_hdct_tongtien);
            ib_delete = itemView.findViewById(R.id.btn_delete_thanhtoan);
        }

        public ImageView getIv_xe() {
            return iv_xe;
        }

        public TextView getTv_name_xe() {
            return tv_name_xe;
        }

        public TextView getTv_amount() {
            return tv_amount;
        }

        public TextView getTv_price() {
            return tv_price;
        }
        public TextView getTv_total() {
            return tv_total;
        }

        public ImageButton getIb_delete() {
            return ib_delete;
        }
    }

    public void deleteData(){
        SharedPreferences pref = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hoadonchitiet");
        editor.commit();
    }
    public void updateList(List<HoaDonChiTiet> _data){
        data.clear();
        data.addAll(_data);
        notifyDataSetChanged();
    }
}
