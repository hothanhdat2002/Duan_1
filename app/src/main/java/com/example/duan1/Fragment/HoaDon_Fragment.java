package com.example.duan1.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HangXe_Adapter;
import com.example.duan1.Adapter.HoaDon_Adapter;
import com.example.duan1.Adapter.Xe_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.HoaDonDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HoaDon_Fragment extends Fragment {

    private RecyclerView rv;
    private FloatingActionButton fab;
    private HoaDon_Adapter adapter;
    private List<HoaDon> data;
    private EditText et_search;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // lắng nghe key của HoaDonFragment và trả về dữ liệu sau đó cập nhật list
        getParentFragmentManager().setFragmentResultListener("key",
                HoaDon_Fragment.this,new Them_HoaDon_Fragment(){
                    @Override
                    public void onFragmentResult(String requestKey, Bundle result) {
                        super.onFragmentResult(requestKey, result);
                        List<HoaDon> data = (List<HoaDon>) (new HoaDonDAO(getContext()).get());
                        adapter.updateList(data);
                    }
                });
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_hoadon,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab_hoadon);
        rv = view.findViewById(R.id.rv_hoadon);
        et_search =  view.findViewById(R.id.et_search_hoadon);
        data = (new HoaDonDAO(getContext()).get());

        adapter = new HoaDon_Adapter(data,getContext());
        rv.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setFilterTouchesWhenObscured(true);

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Them_HoaDon_Fragment diaLogFragment =Them_HoaDon_Fragment.newInstance(-1,"",0L);
                diaLogFragment.show(fragmentManager,"");
            }
        });
    }
}
