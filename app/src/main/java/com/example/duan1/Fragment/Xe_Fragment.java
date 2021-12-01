package com.example.duan1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HangXe_Adapter;
import com.example.duan1.Adapter.HangXe_Item_Adapter;
import com.example.duan1.Adapter.Xe_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Xe_Fragment extends Fragment {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private List<Xe> data;
    private Xe_Adapter adapter;
    private Spinner sp_hangxe;
    private List<HangXe> listHang;
    private HangXe_Item_Adapter item_adapter;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // lắng nghe key của FRAGMENT B và trả về dữ liệu sau đó cập nhật list
        getParentFragmentManager().setFragmentResultListener("key",
                Xe_Fragment.this,new Them_Xe_Fragment(){
                    @Override
                    public void onFragmentResult(String requestKey, Bundle result) {
                        super.onFragmentResult(requestKey, result);
                        List<Xe> data = (List<Xe>) (new XeDAO(getContext()).get());
                        adapter.updateList(data);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_xe,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab_xe);
        rv = view.findViewById(R.id.rv_xe);
        sp_hangxe = view.findViewById(R.id.sp_hangxe_search);

        data = (new XeDAO(getContext()).get());

        adapter = new Xe_Adapter(data,getContext());
        rv.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2,GridLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);

        //đổ dữ liệu lên spinner;
        listHang = (new HangXeDAO(getContext()).get());
        item_adapter = new HangXe_Item_Adapter(listHang,getContext());
        sp_hangxe.setAdapter(item_adapter);
        sp_hangxe.setSelection(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                Them_Xe_Fragment diaLogFragment = Them_Xe_Fragment.newInstance(-1,"", new byte[0],0,0,
                        0.0,0.0,0.0,0.0,0,"",0);
                diaLogFragment.show(fragmentManager,"");
            }
        });

        sp_hangxe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                HangXe hangXe = (HangXe) parent.getItemAtPosition(position);
//                int id_hang = hangXe.getId();
//                XeDAO dao = new XeDAO(getContext());
//                List<Xe> xe = dao.getByIDHang(id_hang);
//                adapter.updateList(xe);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
