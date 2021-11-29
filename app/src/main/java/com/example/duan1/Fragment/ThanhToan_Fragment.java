package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HoaDonChiTiet_Adapter;
import com.example.duan1.Adapter.ThanhToan_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.HoaDonChiTietDAO;
import com.example.duan1.DAO.HoaDonDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.HoaDonChiTiet;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;


public class ThanhToan_Fragment extends Fragment {
    public static final String TAG = ThanhToan_Fragment.class.getName();
    private RecyclerView rv;
    private ThanhToan_Adapter adapter;
    private ArrayList<HoaDonChiTiet> data;
//    private Button btn_hoantat;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_thanhtoan,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy mã hóa đơn từ fragment hóa đơn chi tiết
        Integer id_hoadon = getArguments().getInt("id_hoadon");
        rv = view.findViewById(R.id.rv_sp_hdct);
//        btn_hoantat = view.findViewById(R.id.btn_hoantat);
        data = (new HoaDonChiTietDAO(getContext()).getByIDSum(id_hoadon));
        adapter = new ThanhToan_Adapter(data,getContext());
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);

//        btn_hoantat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HoaDonChiTietDAO dao = new HoaDonChiTietDAO(getContext());
//                HoaDonDAO hoaDondao = new HoaDonDAO(getContext());
//                dao.deleteByID(id_hoadon);
//                hoaDondao.delete(id_hoadon);
//
//                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction1.replace(R.id.my_frame_layout, new HoaDon_Fragment());
//                fragmentTransaction1.addToBackStack(null);
//                fragmentTransaction1.commit();
//
//            }
//        });
    }

}
