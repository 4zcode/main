package com.example.myapplication;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class SpecialisteDoctorsFragment extends Fragment {

    
    public SpecialisteDoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("akram","we stil alive 30");
        View view = inflater.inflate(R.layout.fragment_specialistes_doctor, container, false);
        RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        String[] sportsCate = getResources().getStringArray(R.array.sports_cate);
        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);
        for (int i = 0; i < sportsList.length; i++) {
            mDoctorsData.add(new Doctors(sportsList[i], sportsInfo[i], sportsCate[i],
                    sportsImageResources.getResourceId(i, 0)));
        }
        DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsData);

        mRecyclerView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return view;
    }

}
