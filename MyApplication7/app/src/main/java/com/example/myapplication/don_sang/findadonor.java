package com.example.myapplication.don_sang;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class findadonor extends Fragment {
    public findadonor() {
        // Required empty public constructor
    }
    private ArrayList<don_de_song> mdonateurdata = new ArrayList<don_de_song>();
    private DBManagerDonateur dbManagerDonateur;
    private RecyclerView mRecyclerView;
    private DonateurAdapter mAdapter;
    private SearchView searchView;
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("Donateur");
    private ProgressDialog mProgressDialog;
    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("donateur","we are in find1");
        View view = inflater.inflate(R.layout.fragment_findadonor, container, false);
        Log.d("donateur","we are in find2");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewd2);
        Log.d("donateur","we are in find3");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        Log.d("donateur","we are in find4");
        searchView = (SearchView) view.findViewById(R.id.search_donateur2);
        Log.d("donateur","we are in find4");
        dbManagerDonateur = new DBManagerDonateur(getContext());
        Log.d("donateur","we are in find6");
        dbManagerDonateur.open();
        Log.d("donateur","we are in find7");
        if (isNetworkAvailable()) {
            Log.d("donateur","we are in find7.0");
            Toast.makeText(this.getContext(), "there is connection", Toast.LENGTH_LONG).show();
            Log.d("donateur","we are in find7.1");
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
            Log.d("donateur","we are in find7.2");
        } else {
            Toast.makeText(this.getContext(), "no connection", Toast.LENGTH_LONG).show();
            Log.d("donateur","we are in find7.3");
        }
        Log.d("donateur","we are in find8");
        mdonateurdata = dbManagerDonateur.listdonateur();
        Log.d("donateur","we are in find9");
        mAdapter = new DonateurAdapter(getContext(), mdonateurdata);
        Log.d("donateur","we are in find10");
        mRecyclerView.setAdapter(mAdapter);
        Log.d("donateur","we are in find11");
        mAdapter.notifyDataSetChanged();
        Log.d("donateur","we are in find12");

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
        Log.d("donateur","we are in find13");
        return view;
    }

    public void readData(FireBaseCallBack fireBaseCallBack) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        Log.d("donateur","we are in find7.4");

        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(isNetworkAvailable()){dbManagerDonateur.deleteall();}
                Log.d("donateur","we are in find7.5");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("_ID_firebase").exists() &&ds.child("imaged").exists() && ds.child("fullname").exists() && ds.child("adressd").exists() && ds.child("contact").exists()&& ds.child("grsanguin").exists()&& ds.child("age").exists() ;
                    if (Is_Exist) {
                        String id_firebase = ds.child("_ID_firebase").getValue(String.class);
                        String age=ds.child("age").getValue(String.class);
                        String Name = ds.child("fullname").getValue(String.class);
                        String Place = ds.child("adressd").getValue(String.class);
                        String Grsanguin = ds.child("grsanguin").getValue(String.class);
                        String Phone = ds.child("contact").getValue(String.class);
                        String ImageUrl = ds.child("imaged").getValue(String.class);
                        if (dbManagerDonateur.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManagerDonateur.update(id_firebase, Name, Place, Phone,age,Grsanguin,ImageUrl);
                        } else {
                            dbManagerDonateur.insert(id_firebase,Name,Place,Phone,age,Grsanguin,ImageUrl);

                        }
                    }
                }
                mdonateurdata= dbManagerDonateur.listdonateur();
                mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);
                mProgressDialog.dismiss();
                Log.d("donateur","we are in find7.6");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public boolean isNetworkAvailable() {
        boolean HaveConnectWIFI = false;
        boolean HaveConnectMobile = false;
        AppCompatActivity test = new AppCompatActivity();
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
