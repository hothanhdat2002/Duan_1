package com.example.duan1.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HangXe_Item_Adapter;
import com.example.duan1.Adapter.HoaDonChiTiet_Adapter;
import com.example.duan1.Adapter.Xe_Item_Adapter;
import com.example.duan1.DAO.HangXeDAO;
import com.example.duan1.DAO.HoaDonChiTietDAO;
import com.example.duan1.DAO.XeDAO;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;
import com.example.duan1.Model.HoaDonChiTiet;
import com.example.duan1.Model.Xe;
import com.example.duan1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTiet_Fragment extends Fragment {
    public static final String TAG = HoaDonChiTiet_Fragment.class.getName();
    private RecyclerView rv_hdct;
    private ImageButton ib_giamxe, ib_tangxe;
    private TextView tv_amount_xe,tv_totallist;
    private Spinner sp_xe;
    private List<Xe> listXe;
    private ImageButton btn_refresh;
    private List<HoaDonChiTiet> listHDCT;
    private Xe_Item_Adapter item_adapter;
    private int count = 1;
    private int xe_id;
    private int id_hoadon;
    int xeDaThem = 0;
    private Button btn_add,btn_save,btn_thanhtoan;
    private ArrayList<HoaDonChiTiet> data = new ArrayList<HoaDonChiTiet>();
    private HoaDonChiTiet_Adapter adapter;
    HoaDonChiTietDAO dao = new HoaDonChiTietDAO(getContext());;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_hoadonchitiet,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ib_giamxe =  view.findViewById(R.id.ib_giamxe);
        ib_tangxe = view.findViewById(R.id.ib_tangxe);
        tv_amount_xe = view.findViewById(R.id.tv_amount_xe);
        rv_hdct = view.findViewById(R.id.rv_hdct);
        btn_add = view.findViewById(R.id.btn_add_hdct);
        btn_save = view.findViewById(R.id.btn_save_hdct);
        tv_totallist = view.findViewById(R.id.tv_totallist);
        btn_thanhtoan = view.findViewById(R.id.btn_thanhtoan);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        // lấy mã hóa đơn từ fragment hóa đơn
        id_hoadon = getArguments().getInt("id_hoadon");

        //đổ dữ liệu lên spinner;
        sp_xe = view.findViewById(R.id.sp_xe_id);
        listXe = (new XeDAO(getContext()).get());
        item_adapter = new Xe_Item_Adapter(listXe,getContext());
        sp_xe.setAdapter(item_adapter);
        sp_xe.setSelection(0);

        //nút tăng giảm số lượng
        ib_tangxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment();
            }
        });
        ib_giamxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });

        //khi click 1 item spinner thì sẽ lấy được ID
        sp_xe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Xe xe = (Xe) parent.getItemAtPosition(position);
                xe_id = xe.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        loadData();
        buildRecyclerView();
        XeDAO xeDAO = new XeDAO(getContext());
        listHDCT = (new HoaDonChiTietDAO(getContext()).get());
        //nút thêm sản phẩm vào recycleview
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(tv_amount_xe.getText().toString());
                int i = 0;

                //nếu id_xe trùng nhau thì chỉ tăng số lượng
                for (i = 0;i<data.size();i++) {
                    if (data.get(i).getId_xe()==xe_id){
                        amount = amount + data.get(i).getAmount();
                        data.get(i).setAmount(amount);

                        adapter.notifyItemChanged(i);

                        break;
                    }
                }
                if (i>=data.size()) {
                    data.add(new HoaDonChiTiet(id_hoadon, xe_id, amount));
                }
                double sl = 0;
                for (i = 0;i<data.size();i++) {
                    sl += (data.get(i).getAmount() * listXe.get(i).getPrice());
                    tv_totallist.setText(sl + "");
                }
                tv_amount_xe.setText(String.valueOf(1));
//                // notifying adapter when new data added.
                adapter.notifyItemInserted(data.size());

            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sl1 = 0;
                sl1  = totalist();
                tv_totallist.setText(sl1 + "");
            }
        });

        //Lưu sản phẩm từ recycle view vào database HDCT
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int xeTrongKho = xeDAO.sumAmount(xe_id);
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getId_xe() == xe_id) {
                            hoaDonChiTiet.setId(data.get(i).getId());
                            hoaDonChiTiet.setId_hoadon(data.get(i).getId_hoadon());
                            hoaDonChiTiet.setId_xe(data.get(i).getId_xe());
                            hoaDonChiTiet.setAmount(data.get(i).getAmount());
                            for (int j = 0; j < data.size(); j++) {
                                hoaDonChiTiet = data.get(j);
                                dao.insert(hoaDonChiTiet);
                            }
                        };
                    }
                Toast.makeText(getContext(), "Product saved successfully", Toast.LENGTH_SHORT).show();
            }
        });


        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ThanhToan_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("id_hoadon", id_hoadon);
                // Set Fragmentclass Arguments
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.my_frame_layout, fragment);
                fragmentTransaction.addToBackStack(ThanhToan_Fragment.TAG);
                fragmentTransaction.commit();
            }
        });

    }
    public double totalist(){
        double sl2=0;
        for (int i = 0;i<data.size();i++) {
            sl2 += (data.get(i).getAmount() * listXe.get(i).getPrice());

        }
        return sl2;
    }
    @Override
    public void onResume() {
        super.onResume();
        xeDaThem = dao.sumBike(xe_id);

    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new HoaDonChiTiet_Adapter(data, getContext());

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_hdct.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        rv_hdct.setLayoutManager(manager);

        // setting adapter to our recycler view.
        rv_hdct.setAdapter(adapter);
    }


    //hàm tăng giảm số lượng
    private void increment(){
        String currentValue = tv_amount_xe.getText().toString();
        count = Integer.parseInt(currentValue);
        count++;
        tv_amount_xe.setText("" + count);

    }
    private void decrement(){
        String currentValue = tv_amount_xe.getText().toString();
        count = Integer.parseInt(currentValue);
        if (count<=1){
            count =1;
        }else {
            count--;
            tv_amount_xe.setText("" + count);
        }
    }

    private void loadData() {
        // shared preferences.
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        String json = sharedPreferences.getString("hoadonchitiet", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<HoaDonChiTiet>>() {}.getType();
        data = gson.fromJson(json, type);

        if (data == null) {
            data = new ArrayList<>();
        }
        Toast.makeText(getContext(), "Get Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
    private void saveData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        String json = gson.toJson(data);

        editor.putString("hoadonchitiet", json);

        editor.apply();
    }
}
