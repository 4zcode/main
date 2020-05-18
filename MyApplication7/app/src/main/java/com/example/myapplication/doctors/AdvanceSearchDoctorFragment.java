package com.example.myapplication.doctors;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Wilaya;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.utilities.PreferenceUtilities.getLastSpeciality;
import static com.example.myapplication.utilities.tools.getCommuns;
import static com.example.myapplication.utilities.tools.getWilayasList;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchDoctorFragment extends Fragment  {

    public static final String TAG = AdvanceSearchDoctorFragment.class.getSimpleName();

    private ArrayList<Doctors> mDoctorsData;
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
    private RecyclerView mRecyclerView;
    private DBManagerDoctor dbManager;
    private DoctorsAdapter mAdapter;
    private SearchView searchView;
    private Spinner spinnerWilaya, spinnerCommuns;
    private TextView nbrItem;
    private ArrayAdapter<CharSequence>  willayaCodeAdapter,commmunsCodeAdapter;
    private LinearLayoutManager layoutManager;
    private NestedScrollView nestedScrollView;
    private Integer count = 0;
    private String wilaya="Blida", commune;
    private String mSpeciality;

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
        searchView = view.findViewById(R.id.search_doctor);
        spinnerWilaya = view.findViewById(R.id.spinner_doctor_wilaya);
        spinnerCommuns = view.findViewById(R.id.spinner_doctor_communs);
        nestedScrollView = view.findViewById(R.id.doctor_nestedscroll);
        layoutManager = new LinearLayoutManager(getActivity());
        mSpeciality = getLastSpeciality(getContext());
        Log.d("SpecialityTest","speciality is : "+mSpeciality);
        dbManager = new DBManagerDoctor(getActivity());
        dbManager.open();
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        new UpdateDoctorListTask().execute();
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > oldScrollY){
                // Log.d("FileAkramTest","scrollDown");
             }
             if (scrollY < oldScrollY){
               //  Log.d("FileAkramTest","scrollup");
             }
             if (scrollY == 0){
              //   Log.d("FileAkramTest","top scroll");
             }
             if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                 count++;
                 mDoctorsData = dbManager.listdoctors(count,"",wilaya,getLastSpeciality(getContext()));
                 mAdapter.notifyDataSetChanged();

             }
            }
        });
       willayaCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item,getWilayasList());

        willayaCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWilaya.setAdapter(willayaCodeAdapter);
        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCommuns.setVisibility(View.VISIBLE);
                wilaya = String.valueOf(spinnerWilaya.getSelectedItem());
                commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getActivity(),android.R.layout.simple_spinner_item,getCommuns(wilaya));
                commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCommuns.setAdapter(commmunsCodeAdapter);
                if (position == 0) {
                    spinnerWilaya.setSelection(0);
                }
                //
                mDoctorsData = dbManager.listdoctors(count,"",wilaya,getLastSpeciality(getContext()));
                mAdapter = new DoctorsAdapter(getActivity(), mDoctorsData);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query,wilaya,getLastSpeciality(getContext()));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText,wilaya,getLastSpeciality(getContext()));
                return true;
            }
        });
        return view;
    }

    public void readData(FireBaseCallBack fireBaseCallBack) {
        DoctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // dbManager.deleteAll();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

   public class UpdateDoctorListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
           if (isNetworkAvailable(getContext())) {
                readData(new FireBaseCallBack() {
                @Override
                public void onCallBack(ArrayList<String> list) {

                }
            });
        }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(),"Updated",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            mSpeciality = getLastSpeciality(getContext());
            mDoctorsData = dbManager.listdoctors(count,"",wilaya,mSpeciality);
            mAdapter = new DoctorsAdapter(getActivity(), mDoctorsData);
            mRecyclerView.setAdapter(mAdapter);        }
    }
}