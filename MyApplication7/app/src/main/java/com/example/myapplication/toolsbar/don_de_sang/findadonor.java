package com.example.myapplication.toolsbar.don_de_sang;

import android.app.ProgressDialog;
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

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.myapplication.utilities.tools.getCommuns;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;


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


    private String[] mAdress={"Médéa","Médéa","Ain Bensultan"};


    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence>  commmunsCodeAdapter;



    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findadonor, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewd2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        searchView = (SearchView) view.findViewById(R.id.search_donateur2);


        spinnerWilaya = view.findViewById(R.id.spinner_find_wilaya);
        spinnerCommuns = view.findViewById(R.id.spinner_find_communs);


        dbManagerDonateur = new DBManagerDonateur(getContext());
        dbManagerDonateur.open();


        if (isNetworkAvailable(getContext())) {
            Toast.makeText(this.getContext(), "there is connection", Toast.LENGTH_LONG).show();
            readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        } else {
            Toast.makeText(this.getContext(), "no connection", Toast.LENGTH_LONG).show();
        }


        mdonateurdata = dbManagerDonateur.listdonateur();
        Log.d("donationts","first length : "+ mdonateurdata.size());

       mAdapter = new DonateurAdapter(getContext(), mdonateurdata);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();





        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCommuns.setVisibility(View.VISIBLE);
                mAdress[2] = String.valueOf(spinnerWilaya.getSelectedItem());
                commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, getCommuns(position));
                commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCommuns.setAdapter(commmunsCodeAdapter);
                //


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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
                if(isNetworkAvailable(getContext())){dbManagerDonateur.deleteall();}
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
                            dbManagerDonateur.update(id_firebase, Name, Place,"0","0", Phone,age,Grsanguin,ImageUrl);
                        } else {
                            dbManagerDonateur.insert(id_firebase,Name,Place,"0","0",Phone,age,Grsanguin,ImageUrl);

                        }
                    }
                }
                mdonateurdata= dbManagerDonateur.listdonateur();
               mAdapter = new DonateurAdapter(getContext(),mdonateurdata);
                mRecyclerView.setAdapter(mAdapter);

           mProgressDialog.dismiss();


           Log.d("donationts","new length : "+ mdonateurdata.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
