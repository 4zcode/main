package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialisteDoctorsFragment extends Fragment {


    public SpecialisteDoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("akram","we stil alive 30");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_specialistes_doctor, container, false);
    }

}
