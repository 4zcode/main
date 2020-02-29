package com.example.myapplication.doctors;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearchDoctorFragment extends Fragment {


    public AdvanceSearchDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("akram","we stil alive advanceSearchFragment 1");
        View view = inflater.inflate(R.layout.fragment_doctor_advance_search, container, false);
        RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler_advanced_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
        Log.d("akram","we stil alive fragment advanceSearchFragment 5");

        String[] NameDoctors = getResources().getStringArray(R.array.doctor_names);
        Log.d("akram","we stil alive advanceSearchFragment 6");

        String[] PlaceDoctors = getResources().getStringArray(R.array.doctor_place);
        Log.d("akram","we stil alive advanceSearchFragment 7");

        String[] SexDactors = getResources().getStringArray(R.array.doctor_sex);
        Log.d("akram","we stil alive advanceSearchFragment 8");

        for (int i = 0; i < NameDoctors.length; i++) {
            mDoctorsData.add(new Doctors(NameDoctors[i], PlaceDoctors[i], SexDactors[i]));
        }
        Log.d("akram","we stil alive advanceSearchFragment 9");

        DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsData);
        Log.d("akram","we stil alive advanceSearchFragment 10");

        mRecyclerView.setAdapter(mAdapter);
        Log.d("akram","we stil alive advanceSearchFragment 11");

        // Inflate the layout for this fragment
        return view;
    }

}
