package com.example.myapplication.Hospital;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class HopitalActivity extends AppCompatActivity {
    private ArrayList<Hopital> mhopitaldata = new ArrayList<Hopital>();
    private DBManagerHospital dbManagerHospital;
    private RecyclerView mRecyclerView;
    private HopitalsAdapter mAdapter;
    private SearchView searchView;
    private final DatabaseReference hopitalRefrence = FirebaseDatabase.getInstance().getReference().child("Hopitals");

    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("labo_acttivity_test","we are in 0");
        setContentView(R.layout.activity_hospital);
        Log.d("labo_acttivity_test","we are in 1");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_hopital) ;

        dbManagerHospital = new DBManagerHospital(HopitalActivity.this);
        dbManagerHospital.open();
        if (isNetworkAvailable(this)) {
            Toast.makeText(HopitalActivity.this, "there is connection", Toast.LENGTH_LONG).show();
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        } else {
            Toast.makeText(HopitalActivity.this, "no connection", Toast.LENGTH_LONG).show();
        }

        mhopitaldata = dbManagerHospital.listHospital();

        mAdapter = new HopitalsAdapter(HopitalActivity.this,mhopitaldata);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
    }

    public void readData(FireBaseCallBack fireBaseCallBack) {
        hopitalRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("hospital_ID_Firebase").exists() &&ds.child("imageResource").exists() && ds.child("hopitalName").exists() && ds.child("hopitalPlace").exists() && ds.child("hopitalContact").exists() ;
                    if (Is_Exist) {
                        String id_firebase = ds.child("hospital_ID_Firebase").getValue(String.class);
                        String Name = ds.child("hopitalName").getValue(String.class);
                        String Place = ds.child("hopitalPlace").getValue(String.class);
                        String Phone = ds.child("hopitalContact").getValue(String.class);
                        String ImageUrl = ds.child("imageResource").getValue(String.class);
                        if (dbManagerHospital.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManagerHospital.update(id_firebase, Name, Place, Phone,ImageUrl);
                        } else {
                            dbManagerHospital.insert(id_firebase,Name,Place,Phone,ImageUrl);

                        }
                    }
                }
                mhopitaldata= dbManagerHospital.listHospital();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
