package com.example.myapplication.doctors.speciality;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
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
    private RelativeLayout relativeLayout;

    public SpecialisteDoctorsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialistes_doctor, container, false);
        final RecyclerView mRecyclerView =  view.findViewById(R.id.doctor_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ArrayList<DoctorsSpecialistes> mDoctorsSpecialistes = new ArrayList<DoctorsSpecialistes>();
        String[] SpecialisteNames = getResources().getStringArray(R.array.speciality);
      //  TypedArray doctorSpecialistesImages = getResources().obtainTypedArray(R.array.sports_images);
       /* mRecyclerView.setOnScrollChangeListener(new RecyclerView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > oldScrollY){
                    Log.d("FileAkramTest","scrollDown");
                }
                if (scrollY < oldScrollY){
                    Log.d("FileAkramTest","scrollup");
                }
                if (scrollY == 0){
                    Log.d("FileAkramTest","top scroll");
                }
                if (scrollY == (v.getMeasuredHeight())){
                    Log.d("FileAkramTest","end scroll");
                }
            }
        });


        */


        for (int i = 0; i <SpecialisteNames.length ; i++) {
            mDoctorsSpecialistes.add(new DoctorsSpecialistes(SpecialisteNames[i],R.drawable.cardiologue1));
        }
        mAdapter = new DoctorsSpecialisteAdapter( getActivity(),getActivity(), mDoctorsSpecialistes);
        mRecyclerView.setAdapter(mAdapter);
        new UpdateSpecialistNumberTask().execute();
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
        }
    }

}
