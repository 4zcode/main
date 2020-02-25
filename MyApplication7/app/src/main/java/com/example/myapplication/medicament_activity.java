package com.example.myapplication;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class medicament_activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_medicament);
        super.onCreate(savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecycleView1);
        ArrayList<Medicament> mSportsData = new ArrayList<Medicament>();
        MedicamentsAdapter mAdapter = new MedicamentsAdapter(this, mSportsData);
        String[] sportsList = getResources().getStringArray(R.array.doctor_names);
        String[] sportsInfo = getResources().getStringArray(R.array.doctor_sex);
        String[] sportsCate = getResources().getStringArray(R.array.doctor_place);
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Medicament(sportsList[i], sportsInfo[i], sportsCate[i]));
        }

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}


