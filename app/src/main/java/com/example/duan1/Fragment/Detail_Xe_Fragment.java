package com.example.duan1.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HangXe_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Detail_Xe_Fragment extends Fragment {
    TextView tv_volum,tv_speed,tv_mass,tv_name,tv_engine1,tv_fuel1,tv_detail_price,tv_rp,tv_roadking;
    TextView tv_detail_name,tv_engine,tv_fuel,tv_cm,tv_kmh,tv_kg,tv_amount1,tv_amount_detail;
    ImageView iv_detail_image,iv_detail_logo;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_item_detail_xe,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_volum  = view.findViewById(R.id.tv_volum);
        tv_speed  = view.findViewById(R.id.tv_speed);
        tv_mass  = view.findViewById(R.id.tv_mass);
        tv_name  = view.findViewById(R.id.tv_name);
        tv_engine1  = view.findViewById(R.id.tv_engine1);
        tv_detail_price  = view.findViewById(R.id.tv_detail_price);
        tv_rp  = view.findViewById(R.id.tv_rp);
        tv_roadking  = view.findViewById(R.id.tv_roadking);
        iv_detail_image  = view.findViewById(R.id.iv_detail_image);
        iv_detail_logo  = view.findViewById(R.id.iv_detail_logo);
        tv_detail_name  = view.findViewById(R.id.tv_detail_name);
        tv_engine  = view.findViewById(R.id.tv_engine);
        tv_fuel1  = view.findViewById(R.id.tv_fuel1);
        tv_fuel  = view.findViewById(R.id.tv_fuel);
        tv_kg  = view.findViewById(R.id.tv_kg);
        tv_cm  = view.findViewById(R.id.tv_cm);
        tv_kmh  = view.findViewById(R.id.tv_kmh);
        tv_amount1  = view.findViewById(R.id.tv_amount1);
        tv_amount_detail  = view.findViewById(R.id.tv_amount_detail);

        int id =  getArguments().getInt("id_xe");
        Xe xe = (new XeDAO(getContext()).getByID(id));
        tv_volum.setText(xe.getVolume()+"");
        tv_speed.setText(xe.getSpeed()+"");
        tv_mass.setText(xe.getMass()+"");
        tv_detail_name.setText(xe.getName()+"");
        tv_engine.setText(xe.getEngine()+"");
        tv_detail_price.setText(xe.getPrice()+"");
        tv_fuel.setText(xe.getFuel()+" L");
        tv_amount_detail.setText(xe.getAmount()+"");
        tv_volum.setTextColor(xe.getColor());
        tv_cm.setTextColor(xe.getColor());
        tv_speed.setTextColor(xe.getColor());
        tv_kmh.setTextColor(xe.getColor());
        tv_mass.setTextColor(xe.getColor());
        tv_kg.setTextColor(xe.getColor());
        tv_name.setTextColor(xe.getColor());
        tv_engine1.setTextColor(xe.getColor());
        tv_fuel1.setTextColor(xe.getColor());
        tv_detail_price.setTextColor(xe.getColor());
        tv_rp.setTextColor(xe.getColor());
        tv_roadking.setTextColor(xe.getColor());
        tv_amount1.setTextColor(xe.getColor());
        //chuyển byte thành hình ảnh
        byte[] images = xe.getImages();
        Bitmap bitmaps = BitmapFactory.decodeByteArray(images,0,images.length);
        iv_detail_image.setImageBitmap(bitmaps);

        //lấy ảnh logo từ bảng hãng xe theo id
        HangXeDAO hangXeDAO = new HangXeDAO(getContext());
        int id_hangxe = xe.getIdhangxe();
        HangXe hangXe = hangXeDAO.getByID(id_hangxe);

        byte[] logo = hangXe.getLogo();
        Bitmap bitmaps1 = BitmapFactory.decodeByteArray(logo,0,logo.length);
        iv_detail_logo.setImageBitmap(bitmaps1);
    }
}
