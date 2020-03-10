package com.example.myapplication.doctors;


import android.os.Bundle;

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

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.Pharmacies.DBManagerph;
import com.example.myapplication.Pharmacies.adapterpharmacies;
import com.example.myapplication.Pharmacies.pharmacies;
import com.example.myapplication.R;

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

        dbManager.insert("akram","medea","0780115465");
        Log.d("DBF","DATA BASE 5");

        mDoctorsData = dbManager.listDoctors();
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
}
