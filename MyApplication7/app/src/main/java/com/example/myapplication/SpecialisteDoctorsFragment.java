package com.example.myapplication;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        Log.d("akram","we stil alive fragment 2");
        RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler);
        Log.d("akram","we stil alive fragment 3");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("akram","we stil alive fragment 4");

        ArrayList<DoctorsSpecialistes> mDoctorsSpecialistes = new ArrayList<DoctorsSpecialistes>();
        Log.d("akram","we stil alive fragment 5");

        String[] SpecialisteNames = getResources().getStringArray(R.array.doctor_specialistes);
        Log.d("akram","we stil alive fragment 6");

        //TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);


        for (int i = 0; i < SpecialisteNames.length; i++) {
            mDoctorsSpecialistes.add(new DoctorsSpecialistes(SpecialisteNames[i]));
        }
        Log.d("akram","we stil alive fragment 9");

        DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsSpecialistes);
        Log.d("akram","we stil alive fragment 10");

        mRecyclerView.setAdapter(mAdapter);
        Log.d("akram","we stil alive fragment 11");

        // Inflate the layout for this fragment
        return view;


       /* ArrayList<Doctors> mDoctorsData = new ArrayList<Doctors>();
        Log.d("akram","we stil alive fragment 5");

        String[] NameDoctors = getResources().getStringArray(R.array.doctor_names);
        Log.d("akram","we stil alive fragment 6");

        String[] PlaceDoctors = getResources().getStringArray(R.array.doctor_place);
        Log.d("akram","we stil alive fragment 7");

        String[] SexDactors = getResources().getStringArray(R.array.doctor_sex);
        Log.d("akram","we stil alive fragment 8");

        for (int i = 0; i < NameDoctors.length; i++) {
            mDoctorsData.add(new Doctors(NameDoctors[i], PlaceDoctors[i], SexDactors[i]));
        }
        Log.d("akram","we stil alive fragment 9");

        DoctorsAdapter mAdapter= new DoctorsAdapter( getActivity(), mDoctorsData);
        Log.d("akram","we stil alive fragment 10");

        mRecyclerView.setAdapter(mAdapter);
        Log.d("akram","we stil alive fragment 11");

        // Inflate the layout for this fragment
        return view;

        */

    }

}
