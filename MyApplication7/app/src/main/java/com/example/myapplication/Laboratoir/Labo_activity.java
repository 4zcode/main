package com.example.myapplication.Laboratoir;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import static java.security.AccessController.getContext;

public class Labo_activity extends AppCompatActivity {
    private ArrayList<Labo> mlabodata = new ArrayList<Labo>();
    private DBManagerLaboratoir dbManagerLaboratoir;
    private RecyclerView mRecyclerView;
    private LabosAdapter mAdapter;
    private SearchView searchView;
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("laboratoir");
    private ProgressDialog mProgressDialog;
    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labo_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.RECYCLERView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView=(SearchView)findViewById(R.id.search_labo) ;

        dbManagerLaboratoir = new DBManagerLaboratoir(Labo_activity.this);
        dbManagerLaboratoir.open();
        if (isNetworkAvailable()) {
            Toast.makeText(Labo_activity.this, "there is connection", Toast.LENGTH_LONG).show();
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        } else {
            Toast.makeText(Labo_activity.this, "no connection", Toast.LENGTH_LONG).show();
        }

        mlabodata = dbManagerLaboratoir.listLabo();

        mAdapter = new LabosAdapter(Labo_activity.this,mlabodata);
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
            mProgressDialog = new ProgressDialog(Labo_activity.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();

        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("labo_ID_Firebase").exists() &&ds.child("imageResource").exists() && ds.child("laboName").exists() && ds.child("laboPlace").exists() && ds.child("laboContact").exists() ;
                    if (Is_Exist) {
                        String id_firebase = ds.child("labo_ID_Firebase").getValue(String.class);
                        String Name = ds.child("laboName").getValue(String.class);
                        String Place = ds.child("laboPlace").getValue(String.class);
                        String Phone = ds.child("laboContact").getValue(String.class);
                        String ImageUrl = ds.child("imageResource").getValue(String.class);
                        if (isNetworkAvailable()){  dbManagerLaboratoir.deleteall();}
                        if (dbManagerLaboratoir.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManagerLaboratoir.update(id_firebase, Name, Place, Phone,ImageUrl);
                        } else {
                            dbManagerLaboratoir.insert(id_firebase,Name,Place,Phone,ImageUrl);

                        }
                    }
                }
                mlabodata= dbManagerLaboratoir.listLabo();
                mAdapter = new LabosAdapter(Labo_activity.this,mlabodata);
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
