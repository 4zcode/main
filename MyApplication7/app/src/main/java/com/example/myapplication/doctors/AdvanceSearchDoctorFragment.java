package com.example.myapplication.doctors;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchDoctorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public  final ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
    public  DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    public  final DatabaseReference DoctorsRef = rootRef.child("Doctor");
    public final ArrayList<String> list = new ArrayList<String>();
    public RecyclerView mRecyclerView;

    public AdvanceSearchDoctorFragment() {
        // Required empty public constructor
    }
    private interface FireBaseCallBack{
       // void onStart();
         void onCallBack(ArrayList<String> list2);
        //void onSuccess(DataSnapshot dataSnapshot);
        //void onFailed(DatabaseError databaseError);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Spinner spinner;
        ArrayAdapter<CharSequence> adapspin;
        DBManagerDoctor dbManager;

      /*  if (isNetworkAvailable()){
              Toast.makeText(getContext(),"there is connection",Toast.LENGTH_LONG);
        }else {Toast.makeText(getContext(),"no connection",Toast.LENGTH_LONG);}
        */
        View view = inflater.inflate(R.layout.fragment_doctor_advance_search, container, false);
        mRecyclerView =  view.findViewById(R.id.doctor_recycler_advanced_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbManager = new DBManagerDoctor(getActivity());
        dbManager.open();
        readData(new FireBaseCallBack() {
            @Override
            public void onCallBack(ArrayList<String> list2) {
                Log.d("doctor_activity_test","inside on callback readdata");

                Log.d("doctor_activity_test",list.toString());
            }
        });
     /*  DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("doctor_activity_test", "avant");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = ds.child("name").getValue(String.class);
                    String Place = ds.child("adress").getValue(String.class);
                    String Phone = ds.child("phone").getValue(String.class);
                    String Spec = ds.child("spec").getValue(String.class);
                    String Sex = ds.child("sex").getValue(String.class);

                    Log.d("doctor_activity_test", Name + " / " + Place + " / " + Phone + " / " + Sex);
                    mDoctorsData.add(new Doctors(Name, Place, Phone, Spec, Sex));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("doctor_activity_test",databaseError.getMessage());
            }
        });

      */

     // DoctorsRef.addListenerForSingleValueEvent(valueEventListener);
        Log.d("DBF","DATA BASE 5");
       /* if (isNetworkAvailable()){
            final ArrayList<Doctors> doctorArray = new ArrayList<>();
            firebaseDatabase.child("doctors").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    doctorArray.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()){doctorArray.add(data.getValue());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
         */
        // mDoctorsData = dbManager.listdoctors();
        Log.d("doctor_activity_test", String.valueOf(mDoctorsData.size()));

       // DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsData);
        Log.d("akram","we stil alive advanceSearchFragment 10");

       // mRecyclerView.setAdapter(mAdapter);
        Log.d("akram","we stil alive advanceSearchFragment 11");

        // Inflate the layout for this fragment
        spinner=(Spinner) view.findViewById(R.id.docspinner);
        if(spinner!=null){spinner.setOnItemSelectedListener(AdvanceSearchDoctorFragment.this);}
        adapspin= ArrayAdapter.createFromResource(getActivity(),R.array.wilaya,android.R.layout.simple_spinner_item);
        adapspin.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapspin);
        }
        return view;
    }
    /*public void readData(final FireBaseCallBack fireBaseCallBack){
        fireBaseCallBack.onStart();
        //  final ArrayList<String> list = new ArrayList<String>();
        DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*Log.d("doctor_activity_test", "avant");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Log.d("doctor_activity_test", "inside for");
                    String Name = ds.child("name").getValue(String.class);
                    String Place = ds.child("adress").getValue(String.class);
                    String Phone = ds.child("phone").getValue(String.class);
                    String Spec = ds.child("spec").getValue(String.class);
                    String Sex = ds.child("sex").getValue(String.class);

                    Log.d("doctor_activity_test", Name + " / " + Place + " / " + Phone + " / " + Sex);
                    //mDoctorsData.add(new Doctors(Name, Place, Phone, Spec, Sex));
                    list.add(Name);
                }
                fireBaseCallBack.onSuccess(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("doctor_activity_test",databaseError.getMessage());
                fireBaseCallBack.onFailed(databaseError);
            }
        });
    }

     */
    public void readData(FireBaseCallBack fireBaseCallBack){
        DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("doctor_activity_test", "avant mData");
                Log.d("doctor_activity_test", "avant");
                Log.d("doctor_activity_test","Doctor has : " + String.valueOf(dataSnapshot.getChildrenCount())+ " children");
                Log.d("doctor_activity_test","Doctor has DoctorNamz as children : " + dataSnapshot.child("NameDoctor").getValue() );
                list.add((String) dataSnapshot.child("NameDoctor").getValue());
                Log.d("doctor_activity_test",list.toString());
                String Name = dataSnapshot.child("NameDoctor").getValue().toString();
                mDoctorsData.add(new  Doctors(Name,Name,Name,Name,Name));
                DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsData);
                Log.d("akram","we stil alive advanceSearchFragment 10");

                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("doctor_activity_test",databaseError.getMessage());
            }
        });
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /*private boolean isNetworkAvailable(){
        boolean HaveConnectWIFI = false;
        boolean HaveConnectMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : activeNetworkInfo){
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    HaveConnectWIFI= true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    HaveConnectMobile= true;
        }
        return  HaveConnectMobile || HaveConnectWIFI;
    }
    */
}