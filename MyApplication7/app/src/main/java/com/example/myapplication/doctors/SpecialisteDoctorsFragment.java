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
        // Required empty public constructor
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
            mDoctorsData.Add(new Doctors(NameDoctors[i], PlaceDoctors[i], SexDactors[i]));
        }
        Log.d("akram","we stil alive fragment 9");

        DoctorsSpecialisteAdapter mAdapter= new DoctorsSpecialisteAdapter( getActivity(), mDoctorsData);
        Log.d("akram","we stil alive fragment 10");

        mRecyclerView.setAdapter(mAdapter);
        Log.d("akram","we stil alive fragment 11");

        // Inflate the layout for this fragment
        return view;

        */

    }

}
