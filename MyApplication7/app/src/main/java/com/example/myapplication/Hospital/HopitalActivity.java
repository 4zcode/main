package com.example.myapplication.Hospital;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class HopitalActivity extends AppCompatActivity {
    private DBManagerHospital dbManagerHospital;
    private SearchView searchView;
    private HopitalsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_activity);
        RecyclerView mRecyclerView;
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search_hopital);
        ArrayList<Hopital> HopitalItems = new ArrayList<Hopital>();
        dbManagerHospital = new DBManagerHospital(this);
        dbManagerHospital.open();
        HopitalItems = dbManagerHospital.listHopital();
        adapter = new HopitalsAdapter(this,HopitalItems);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HopitalActivity.this));
        dbManagerHospital.close();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               adapter.filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }
}
