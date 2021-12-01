package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HangXe_Adapter;
import com.example.duan1.Adapter.Home_HangXe_Adapter;
import com.example.duan1.Adapter.Home_Xe_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Home_Fragment extends Fragment {
    private RecyclerView rv_hangxe,rv_xe;
    private List<HangXe> data;
    private List<Xe> dataXe;
    private Home_HangXe_Adapter adapterHome;
    private Home_Xe_Adapter adapterXe;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_hangxe = view.findViewById(R.id.rv_hangxe_home);
        rv_xe = view.findViewById(R.id.rv_xe_home);
        data =(new HangXeDAO(getContext()).get());
        dataXe=(new XeDAO(getContext()).get());


        adapterHome = new Home_HangXe_Adapter(data,getContext());
        adapterXe = new Home_Xe_Adapter(dataXe,getContext());

        rv_hangxe.setAdapter(adapterHome);
        rv_xe.setAdapter(adapterXe);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1,GridLayoutManager.HORIZONTAL,false);
        rv_hangxe.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 1,GridLayoutManager.HORIZONTAL,false);
        rv_xe.setLayoutManager(layoutManager1);
    }
}
