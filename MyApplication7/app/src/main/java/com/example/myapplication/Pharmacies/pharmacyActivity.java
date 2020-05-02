package com.example.myapplication.Pharmacies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class pharmacyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<pharmacy> mpharmaciesData = new ArrayList<pharmacy>();
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("pharmacies");
    private RecyclerView mRecyclerView;
    private DBManagerPharmacy dbManager;
    private ProgressDialog mProgressDialog;
    private pharmacyAdapter mAdapter;
    private SearchView searchView;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;


    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);
        mRecyclerView = findViewById(R.id.pharmacy_activity_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search_pharmacy);
        dbManager = new DBManagerPharmacy(this);
        dbManager.open();
        if (isNetworkAvailable(this)) {
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {
                    Log.d("pharmacyTest","pharma finish oncallback ");
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "no connection", Toast.LENGTH_LONG).show();
        }

        mpharmaciesData = dbManager.listPharmacies();
        mAdapter = new pharmacyAdapter(getBaseContext(),mpharmaciesData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        spinner = findViewById(R.id.pharmacy_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) pharmacyActivity.this);
        }
        spinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.wilaya, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(spinnerAdapter);
        }
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

    public void readData(final FireBaseCallBack fireBaseCallBack) {
       if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(pharmacyActivity.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("pharma_ID_Firebase").exists() &&ds.child("imageUrl").exists() && ds.child("thename").exists() && ds.child("theadress").exists() && ds.child("phone").exists() && ds.child("oppen").exists() &&ds.child("close").exists();
                    if (Is_Exist) {
                        String id_firebase = ds.child("pharma_ID_Firebase").getValue(String.class);
                        String Name = ds.child("thename").getValue(String.class);
                        String Place = ds.child("theadress").getValue(String.class);
                        String Phone = ds.child("phone").getValue(String.class);
                        String open = ds.child("oppen").getValue(String.class);
                        String close= ds.child("close").getValue(String.class);
                        String ImageUrl = ds.child("imageUrl").getValue(String.class);
                        if (isNetworkAvailable(getBaseContext())){  dbManager.deleteall();}
                        if (dbManager.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManager.update(id_firebase, Name, Place, Phone, open, close,ImageUrl);
                        } else {

                            dbManager.insert(id_firebase, Name, Place, Phone, open, close,ImageUrl);

                        }
                    } else Log.d("pharmacy_test","is not exist");
                }
                mpharmaciesData= dbManager.listPharmacies();
                mAdapter = new pharmacyAdapter(getBaseContext(),mpharmaciesData);
                mRecyclerView.setAdapter(mAdapter);
               mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("pharmacyTest","error : "+databaseError.getMessage());
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
