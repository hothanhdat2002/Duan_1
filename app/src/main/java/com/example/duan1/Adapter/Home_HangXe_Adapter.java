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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.Fragment.Them_HangXe_Fragment;
import com.example.duan1.Model.HangXe;
import com.example.duan1.R;

import java.util.List;

public class Home_HangXe_Adapter extends RecyclerView.Adapter<Home_HangXe_Adapter.ViewHolder>{
    private List<HangXe> data;

    private Context context;

    public Home_HangXe_Adapter(List<HangXe> _data, Context _context){
        this.data = _data;
        this.context = _context;
    }

    //hàm lấy index khi longClick
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_home_hangxe,parent,false);
        return new ViewHolder(view);
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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_Logo;

        public ViewHolder( View itemView) {
            super(itemView);
       ;
            iv_Logo = itemView.findViewById(R.id.iv_logo_home);

        }
        public ImageView getIv_Logo() {
            return iv_Logo;
        }

    }

}
