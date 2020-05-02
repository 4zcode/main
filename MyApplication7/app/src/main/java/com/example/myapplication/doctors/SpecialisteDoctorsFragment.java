package com.example.myapplication.doctors;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class SpecialisteDoctorsFragment extends Fragment {
    public SpecialisteDoctorsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialistes_doctor, container, false);
        RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<DoctorsSpecialistes> mDoctorsSpecialistes = new ArrayList<DoctorsSpecialistes>();
        String[] SpecialisteNames = getResources().getStringArray(R.array.doctor_specialistes);
        TypedArray doctorSpecialistesImages = getResources().obtainTypedArray(R.array.sports_images);


        for (int i = 0; i < SpecialisteNames.length; i++) {
            mDoctorsSpecialistes.add(new DoctorsSpecialistes(SpecialisteNames[i],doctorSpecialistesImages.getResourceId(i,0)));
        }
        DoctorsSpecialisteAdapter mAdapter= new DoctorsSpecialisteAdapter( getActivity(), mDoctorsSpecialistes);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;

    }

}
