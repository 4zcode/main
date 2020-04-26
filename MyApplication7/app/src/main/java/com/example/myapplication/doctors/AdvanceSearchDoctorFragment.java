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

import static com.example.myapplication.utilities.tools.isNetworkAvailable;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchDoctorFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String TAG = AdvanceSearchDoctorFragment.class.getSimpleName();

    private ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
    private RecyclerView mRecyclerView;
    private DBManagerDoctor dbManager;
    private ProgressDialog mProgressDialog;
    private DoctorsAdapter mAdapter;
    private SearchView searchView;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;


    public AdvanceSearchDoctorFragment() {
    }

    private interface FireBaseCallBack {
        void onCallBack(ArrayList<String> list);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_advance_search, container, false);
        mRecyclerView = view.findViewById(R.id.doctor_recycler_advanced_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = view.findViewById(R.id.search_doctor);
        dbManager = new DBManagerDoctor(getActivity());
        dbManager.open();
        if (isNetworkAvailable(getContext())) {
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

        DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("Doctor_ID_Firebase").exists() &&ds.child("mImageUrl").exists() && ds.child("NameDoctor").exists() && ds.child("PlaceDoctor").exists() && ds.child("phone").exists() && ds.child("Speciality").exists() &&ds.child("SexDoctor").exists();
                    if (Is_Exist) {
                        String id_firebase = ds.child("Doctor_ID_Firebase").getValue(String.class);
                        String Name = ds.child("NameDoctor").getValue(String.class);
                        String Place = ds.child("PlaceDoctor").getValue(String.class);
                        String Phone = ds.child("phone").getValue(String.class);
                        String Spec = ds.child("Speciality").getValue(String.class);
                        String Sex = ds.child("SexDoctor").getValue(String.class);
                        String ImageUrl = ds.child("mImageUrl").getValue(String.class);

                        if (dbManager.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                            dbManager.update(id_firebase, Name, Place, Phone, Spec, Sex,ImageUrl);
                        } else {
                            dbManager.insert(id_firebase, Name, Place, Phone, Spec, Sex,ImageUrl);
                        }
                    }
                }
                mDoctorsData = dbManager.listdoctors();
                mAdapter = new DoctorsAdapter(getActivity(), mDoctorsData);
                mRecyclerView.setAdapter(mAdapter);
                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
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