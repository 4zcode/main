package com.example.myapplication.doctors;


import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
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


    public AdvanceSearchDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Spinner spinner;
        ArrayAdapter<CharSequence> adapspin;
        DBManagerDoctor dbManager;


        Log.d("akram","we stil alive advanceSearchFragment 1");
        View view = inflater.inflate(R.layout.fragment_doctor_advance_search, container, false);
        RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler_advanced_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
        Log.d("akram","we stil alive fragment advanceSearchFragment 5");

        /*String[] NameDoctors = getResources().getStringArray(R.array.doctor_names);
        Log.d("akram","we stil alive advanceSearchFragment 6");
        String[] PlaceDoctors = getResources().getStringArray(R.array.doctor_place);
        Log.d("akram","we stil alive advanceSearchFragment 7");
        String[] SexDactors = getResources().getStringArray(R.array.doctor_sex);
        Log.d("akram","we stil alive advanceSearchFragment 8");
        for (int i = 0; i < NameDoctors.length; i++) {
            mDoctorsData.add(new Doctors(NameDoctors[i], PlaceDoctors[i], SexDactors[i]));
        }*/
        Log.d("DBF","DATA BASE 1");

        dbManager = new DBManagerDoctor(getActivity());
        Log.d("DBF","DATA BASE 2");

        dbManager.open();
        Log.d("DBF","DATA BASE 3");
        FirebaseAuth firebaseAuth;
        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference firebaseDatabase;
        firebaseDatabase= FirebaseDatabase.getInstance().getReference();


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
        mDoctorsData = dbManager.listdoctors();
        Log.d("akram","we stil alive advanceSearchFragment 9");

        DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsData);
        Log.d("akram","we stil alive advanceSearchFragment 10");

        mRecyclerView.setAdapter(mAdapter);
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
    }*/
}