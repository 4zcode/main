package com.example.myapplication.don_sang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class donateuractivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donateuractivity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewd);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_donateur) ;

        dbManagerDonateur = new DBManagerDonateur(donateuractivity.this);
        dbManagerDonateur.open();
        if (isNetworkAvailable()) {
            Toast.makeText(donateuractivity.this, "there is connection", Toast.LENGTH_LONG).show();
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        } else {
            Toast.makeText(donateuractivity.this, "no connection", Toast.LENGTH_LONG).show();
        }

        mdonateurdata = dbManagerDonateur.listdonateur();

        mAdapter = new DonateurAdapter(donateuractivity.this,mdonateurdata);
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
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(donateuractivity.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();

        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(isNetworkAvailable()){dbManagerDonateur.deleteall();}
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("_ID_firebase").exists() &&ds.child("imaged").exists() && ds.child("fullname").exists() && ds.child("adressd").exists() && ds.child("contact").exists()&& ds.child("grsanguin").exists() ;
                    if (Is_Exist) {
                        String id_firebase = ds.child("_ID_firebase").getValue(String.class);
                        String Name = ds.child("fullname").getValue(String.class);
                        String Place = ds.child("adressd").getValue(String.class);
                        String Grsanguin = ds.child("grsanguin").getValue(String.class);
                        String Phone = ds.child("contact").getValue(String.class);
                        String ImageUrl = ds.child("imaged").getValue(String.class);
                        if (dbManagerDonateur.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManagerDonateur.update(id_firebase, Name, Place, Phone,Grsanguin,ImageUrl);
                        } else {
                            dbManagerDonateur.insert(id_firebase,Name,Place,Phone,Grsanguin,ImageUrl);

                        }
                    }
                }
                mdonateurdata= dbManagerDonateur.listdonateur();
                mAdapter = new DonateurAdapter(donateuractivity.this,mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);
                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public boolean isNetworkAvailable() {
        boolean HaveConnectWIFI = false;
        boolean HaveConnectMobile = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
