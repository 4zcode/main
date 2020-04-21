package com.example.myapplication.Pharmacies;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

public class pharmacyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DBManagerPharmacy dbManager;
    private Spinner spinner;
    private pharmacyAdapter mAdapter;
    private ArrayAdapter<CharSequence> spinnerAddapter;
    private ArrayList<pharmacy> pharmacy_data = new ArrayList<pharmacy>();
    private RecyclerView mRecyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);
        searchView =findViewById(R.id.search_pharmacy);
        dbManager = new DBManagerPharmacy(this);
        dbManager.open();
        pharmacy_data = dbManager.listPharmacies();
        mRecyclerView=(RecyclerView) findViewById(R.id.recycle);
        mAdapter=new pharmacyAdapter(this,pharmacy_data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(pharmacyActivity.this));
        spinner=(Spinner) findViewById(R.id.phspinner);
        if(spinner!=null){spinner.setOnItemSelectedListener(pharmacyActivity.this);}
        spinnerAddapter=ArrayAdapter.createFromResource(pharmacyActivity.this, R.array.wilaya,android.R.layout.simple_spinner_item);
        spinnerAddapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(spinnerAddapter);
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
