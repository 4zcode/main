package com.example.myapplication.doctors.speciality;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.utilities.PreferenceUtilities.initialSpecDB;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;


public class SpecialisteDoctorsFragment extends Fragment {

    private DoctorsSpecialisteAdapter mAdapter;

    public SpecialisteDoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialistes_doctor, container, false);
        RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ArrayList<DoctorsSpecialistes> mDoctorsSpecialistes = new ArrayList<DoctorsSpecialistes>();
        String[] SpecialisteNames = getResources().getStringArray(R.array.speciality);
      //  TypedArray doctorSpecialistesImages = getResources().obtainTypedArray(R.array.sports_images);

        for (int i = 0; i < SpecialisteNames.length; i++) {
            mDoctorsSpecialistes.add(new DoctorsSpecialistes(SpecialisteNames[i],R.drawable.cardiologue1));
        }
        mAdapter = new DoctorsSpecialisteAdapter( getActivity(),getActivity(), mDoctorsSpecialistes);
        mRecyclerView.setAdapter(mAdapter);
        new UpdateSpecialistNumberTask().execute();
        // Inflate the layout for this fragment
        return view;
    }




    public class UpdateSpecialistNumberTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (isNetworkAvailable(getContext())) {
                initialSpecDB(getContext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(),"yes",Toast.LENGTH_LONG).show();
        }
    }

}