package com.example.duan1.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Fragment.Them_Xe_Fragment;
import com.example.duan1.Model.HangXe;
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
        return new ViewHolder(view);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_Name;
        private final TextView tv_Price;
        private final TextView tv_Amount;
        private final ImageView iv_Xe;
        private final ImageView iv_Logo;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.tv_name_xe_home);
            tv_Price = itemView.findViewById(R.id.tv_price_home);
            tv_Amount = itemView.findViewById(R.id.tv_amount_home);
            iv_Xe = itemView.findViewById(R.id.iv_xe_home);
            iv_Logo = itemView.findViewById(R.id.iv_hangxe_home);

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


    }
}
