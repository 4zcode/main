package com.example.myapplication.Laboratoir;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class LaboActivity extends AppCompatActivity {

    private DBManagerLaboratoir dbManagerLaboratoir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labo);
        RecyclerView mRecyclerView;
        final LabosAdapter mAdapter;
        final ArrayList<Labo> mLaboData = new ArrayList<Labo>();

        //TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        mRecyclerView = (RecyclerView) findViewById(R.id.RECYCLERView);

        ArrayList<Labo> linkedList = new ArrayList<Labo>();
        dbManagerLaboratoir = new DBManagerLaboratoir(this);
        dbManagerLaboratoir.open();
        linkedList = DBManagerLaboratoir.listLabo();
        LabosAdapter ada= new LabosAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(LaboActivity.this));
        DBManagerLaboratoir.close();

    }
}
