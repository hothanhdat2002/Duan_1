package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HangXe_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HangXe_Fragment extends Fragment {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private List<HangXe> data;
    private HangXe_Adapter adapter;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // lắng nghe key của FRAGMENT B và trả về dữ liệu sau đó cập nhật list
        getParentFragmentManager().setFragmentResultListener("key",
                HangXe_Fragment.this,new Them_HangXe_Fragment(){
                    @Override
                    public void onFragmentResult(String requestKey, Bundle result) {
                        super.onFragmentResult(requestKey, result);
                        List<HangXe> data = (List<HangXe>) (new HangXeDAO(getContext()).get());
                        adapter.updateList(data);
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_hangxe,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab_hangxe);
        rv = view.findViewById(R.id.rv_hangxe);
        data =(new HangXeDAO(getContext()).get());

        adapter = new HangXe_Adapter(data,getContext());
        rv.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2,GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Them_HangXe_Fragment diaLogFragment = Them_HangXe_Fragment.newInstance(-1,"",new byte[0]);
                diaLogFragment.show(fragmentManager,"");
            }
        });
    }
}
