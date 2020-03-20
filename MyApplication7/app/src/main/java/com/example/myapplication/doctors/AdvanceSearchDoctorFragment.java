package com.example.myapplication.doctors;


import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchDoctorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
    private RecyclerView mRecyclerView;
    private DBManagerDoctor dbManager;
    private ProgressDialog mProgressDialog;
    private DoctorsAdapter mAdapter;


    public AdvanceSearchDoctorFragment() {
    }

    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Spinner spinner;
        ArrayAdapter<CharSequence> adapspin;
        View view = inflater.inflate(R.layout.fragment_doctor_advance_search, container, false);
        mRecyclerView = view.findViewById(R.id.doctor_recycler_advanced_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbManager = new DBManagerDoctor(getActivity());
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

        mDoctorsData = dbManager.listdoctors();

        mAdapter = new DoctorsAdapter(getActivity(), mDoctorsData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        spinner = view.findViewById(R.id.docspinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(AdvanceSearchDoctorFragment.this);
        }
        adapspin = ArrayAdapter.createFromResource(getActivity(), R.array.wilaya, android.R.layout.simple_spinner_item);
        adapspin.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapspin);
        }
        return view;
    }

    public void readData(FireBaseCallBack fireBaseCallBack) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();

        DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id_firebase = ds.child("Doctor_ID_Firebase").getValue(String.class);
                    String Name = ds.child("NameDoctor").getValue(String.class);
                    String Place = ds.child("PlaceDoctor").getValue(String.class);
                    String Phone = ds.child("phone").getValue(String.class);
                    String Spec = ds.child("spec").getValue(String.class);
                    String Sex = ds.child("SexDoctor").getValue(String.class);
                    if (dbManager.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                        dbManager.update(id_firebase, Name, Place, Phone, Spec, Sex);
                    } else {
                        Log.d("doctor_activity_test", Name + " not exist ");
                        Log.d("doctor_activity_test", id_firebase + " not exist");
                        dbManager.insert(id_firebase, Name, Place, Phone, Spec, Sex);
                    }
                }
                mDoctorsData = dbManager.listdoctors();
                mAdapter = new DoctorsAdapter(getActivity(), mDoctorsData);
                mRecyclerView.setAdapter(mAdapter);
                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("doctor_activity_test", databaseError.getMessage());
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean isNetworkAvailable() {
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