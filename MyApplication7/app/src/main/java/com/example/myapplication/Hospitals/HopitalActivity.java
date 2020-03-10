package com.example.myapplication.Hospitals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DBManagerPharmacie;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.Pharmacies.adapterpharmacies;
import com.example.myapplication.Pharmacies.pharmaciesinit;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HopitalActivity extends AppCompatActivity {
    private DBManagerHospital dbManagerHospital;
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

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ArrayList<Hopital> linkedList = new ArrayList<Hopital>();
        dbManagerHospital = new DBManagerHospital(this);
        dbManagerHospital.open();
        linkedList = dbManagerHospital.listHopital();
        HopitalsAdapter ada= new HopitalsAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HopitalActivity.this));
        dbManagerHospital.close();
    }
}
