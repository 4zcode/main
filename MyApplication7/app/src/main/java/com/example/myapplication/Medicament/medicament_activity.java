package com.example.myapplication.Medicament;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class medicament_activity extends AppCompatActivity {
    private MedicamentsAdapter mAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_medicament);
        super.onCreate(savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecycleView1);
        searchView = findViewById(R.id.search_medicament);
        ArrayList<Medicament> mMedicamentsData = new ArrayList<Medicament>();
        String[] medicamentName = getResources().getStringArray(R.array.doctor_names);
        String[] medicamentClass = getResources().getStringArray(R.array.doctor_sex);
        String[] medicamentPrix = getResources().getStringArray(R.array.doctor_place);
        for (int i = 0; i < medicamentName.length; i++) {
            mMedicamentsData.add(new Medicament("Medicament "+ (i+1), "Class i","Prix: 200 DA"));
        }
        mAdapter = new MedicamentsAdapter(this, mMedicamentsData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });


    }

}


