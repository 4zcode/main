package com.example.myapplication.pharma;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME_PHARMACIE;


public class Advancedsearchpahraciesactivity extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayList<pharmacy> mpharmaciesData = new ArrayList<pharmacy>();
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("pharmacies");
    private RecyclerView mRecyclerView;
    private DBManagerPharmacy dbManager;
    private ProgressDialog mProgressDialog;
    private pharmacyAdapter mAdapter;
    private SearchView searchView;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;


    public Advancedsearchpahraciesactivity(){
    }

    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advancedsearchpahraciesactivity, container, false);
        mRecyclerView = view.findViewById(R.id.pharmacies_recycler_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = view.findViewById(R.id.search_pharmacy);
        dbManager = new DBManagerPharmacy(getActivity());
        dbManager.open();
        if (isNetworkAvailable()) {
            Toast.makeText(getContext(), "there is connection", Toast.LENGTH_LONG).show();
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        } else {
            Toast.makeText(getContext(), "no connection", Toast.LENGTH_LONG).show();
        }

        mpharmaciesData = dbManager.listpharmacy();

        mAdapter = new pharmacyAdapter(getActivity(),mpharmaciesData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        spinner = view.findViewById(R.id.docspinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) Advancedsearchpahraciesactivity.this);
        }
        spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.wilaya, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
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
        return view;
    }

    public void readData(FireBaseCallBack fireBaseCallBack) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();

        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isNetworkAvailable()){  dbManager.deleteall();}
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

                        if (dbManager.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManager.update(id_firebase, Name, Place, Phone, open, close,ImageUrl);
                        } else {

                            dbManager.insert(id_firebase, Name, Place, Phone, open, close,ImageUrl);

                        }
                    } else Log.d("pharmacy_test","is not exist");
                }
                mpharmaciesData= dbManager.listpharmacy();
                mAdapter = new pharmacyAdapter(getActivity(),mpharmaciesData);
                mRecyclerView.setAdapter(mAdapter);
                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isNetworkAvailable() {
        boolean HaveConnectWIFI = false;
        boolean HaveConnectMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : activeNetworkInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    HaveConnectWIFI = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    HaveConnectMobile = true;
        }
        return HaveConnectMobile || HaveConnectWIFI;
    }


}