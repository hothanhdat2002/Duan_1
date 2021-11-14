package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.adapter.HarleyDavidsonAdapter;
import com.example.myapplication.adapter.LogoCompanyAdapter;
import com.example.myapplication.modal.HarleyDavidson;
import com.example.myapplication.modal.LogoCompany;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvLogoCompany, rcvHarleyDavidson;
    LogoCompanyAdapter logoCompanyAdapter;
    ArrayList<HarleyDavidson> harleyDavidsonArrayList;
    HarleyDavidsonAdapter harleyDavidsonAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        rcvLogoCompany = findViewById(R.id.rcvLogoCompany);



        logoCompanyAdapter = new LogoCompanyAdapter(this);

        LinearLayoutManager rcvLogo = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvLogoCompany.setLayoutManager(rcvLogo);



        logoCompanyAdapter.setData(getListLogoCompany());
//        harleyDavidsonAdapter.setData(getListHarleyDavidson());

        rcvLogoCompany.setAdapter(logoCompanyAdapter);

// ----------------->

        rcvHarleyDavidson = findViewById(R.id.rcvHarleyDavidson);

        rcvHarleyDavidson.setHasFixedSize(true);
        LinearLayoutManager rcvHarley = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvHarleyDavidson.setLayoutManager(rcvHarley);

        db = FirebaseFirestore.getInstance();
        harleyDavidsonArrayList = new ArrayList<HarleyDavidson>();
        harleyDavidsonAdapter = new HarleyDavidsonAdapter(MainActivity.this, harleyDavidsonArrayList);

        rcvHarleyDavidson.setAdapter(harleyDavidsonAdapter);
        EventChangeListener();


    }

    private void EventChangeListener() {

        db.collection("HarleyDavidson").orderBy("PanAmerica", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("failled",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                harleyDavidsonArrayList.add(dc.getDocument().toObject(HarleyDavidson.class));
                            }
                            harleyDavidsonAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

//    private ArrayList<HarleyDavidson> getListHarleyDavidson() {
//        ArrayList<HarleyDavidson> list = new ArrayList<>();
////
////        list.add(new HarleyDavidson(R.drawable.pan_america,"Pan America","Adventure","28.999$"));
////        list.add(new HarleyDavidson(R.drawable.sport_ster,"Sportster","Sport","27.999$"));
////        list.add(new HarleyDavidson(R.drawable.forty_eight,"Forty-Eight","Cruiser","23.999$"));
////        list.add(new HarleyDavidson(R.drawable.iron,"Iron 883","Cruiser","25.999$"));
////
////        return list;
//    }

    public ArrayList<LogoCompany> getListLogoCompany() {
        ArrayList<LogoCompany> list = new ArrayList<>();

        list.add(new LogoCompany(R.drawable.kawasaki_logo));
        list.add(new LogoCompany(R.drawable.harley_logo));
        list.add(new LogoCompany(R.drawable.yamaha_logo));
        list.add(new LogoCompany(R.drawable.ducati_logo));
        list.add(new LogoCompany(R.drawable.honda_logo));
        return list;
    }
}