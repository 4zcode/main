package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

public class HopitalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_activity);
        RecyclerView mRecyclerView;
        final HopitalsAdapter mAdapter;
        final ArrayList<Hopital> mHopitalsData = new ArrayList<Hopital>();
        String[] HopitalsName = getResources().getStringArray(R.array.doctor_names);
        String[] HopitalsLocation = getResources().getStringArray(R.array.doctor_place);
        String[] HopitalsContact = getResources().getStringArray(R.array.doctor_names);
        //TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);
        for (int i = 0; i < HopitalsName.length; i++) {
            mHopitalsData.add(new Hopital(HopitalsContact[i], HopitalsLocation[i], HopitalsContact[i],R.drawable.doctorf));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new HopitalsAdapter(this, mHopitalsData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HopitalActivity.this));
    }
}
