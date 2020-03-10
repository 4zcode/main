package com.example.myapplication.Pharmacies;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class pharmacies extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DBManagerph dbManager;
    Spinner spinner;
    private DatabaseHelper mDatabase;
    adapterpharmacies ada;
    private ArrayAdapter<CharSequence> adapspin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);
        ArrayList<pharmaciesinit> linkedList = new ArrayList<pharmaciesinit>();
        RecyclerView mreclview;
        dbManager = new DBManagerph(this);
        dbManager.open();
        mDatabase = new DatabaseHelper(this);
        linkedList = dbManager.listPharmacies();
        mreclview=(RecyclerView) findViewById(R.id.recycle);
        ada=new adapterpharmacies(this,linkedList);
        mreclview.setAdapter(ada);
        mreclview.setLayoutManager(new LinearLayoutManager(pharmacies.this));
        spinner=(Spinner) findViewById(R.id.phspinner);
        if(spinner!=null){spinner.setOnItemSelectedListener(pharmacies.this);}
        adapspin=ArrayAdapter.createFromResource(pharmacies.this,R.array.wilaya,android.R.layout.simple_spinner_item);
        adapspin.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapspin);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
