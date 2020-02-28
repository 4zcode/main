package com.example.myapplication.Drugs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class medicament_activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_medicament);
        super.onCreate(savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecycleView1);
        ArrayList<Medicament> mMedicamentsData = new ArrayList<Medicament>();
        MedicamentsAdapter mAdapter = new MedicamentsAdapter(this, mMedicamentsData);
        String[] medicamentName = getResources().getStringArray(R.array.doctor_names);
        String[] medicamentClass = getResources().getStringArray(R.array.doctor_sex);
        String[] medicamentPrix = getResources().getStringArray(R.array.doctor_place);
        for (int i = 0; i < medicamentName.length; i++) {
            mMedicamentsData.add(new Medicament("Medicament "+ (i+1), "Class i","Prix: 200 DA"));
        }

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}


