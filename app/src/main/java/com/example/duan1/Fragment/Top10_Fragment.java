package com.example.duan1.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duan1.Adapter.Top10_Adapter;
import com.example.duan1.DAO.StatisticDAO;
import com.example.duan1.Model.Top;
import com.example.duan1.R;

import java.util.List;

public class Top10_Fragment extends Fragment {
    public  List<Top> data;
    private EditText et_search;
    private RecyclerView rvBookTop;
    private Top10_Adapter adapter;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_top10,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBookTop = view.findViewById(R.id.rvBookTop);
        et_search =  view.findViewById(R.id.et_search_top);
        data =(new StatisticDAO(getContext()).getTop());
        adapter = new Top10_Adapter(data, getContext());
        rvBookTop.setAdapter(adapter);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvBookTop.setLayoutManager(layoutManager);

    }
}

