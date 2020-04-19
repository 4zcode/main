package com.example.myapplication.Laboratoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;

import java.util.ArrayList;

public class Labo_activity extends AppCompatActivity {
    private DBManagerLaboratoir dbManagerLaboratoir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labo_activity);
        RecyclerView mRecyclerView;
        final LabosAdapter mAdapter;
        final ArrayList<Labo> mLaboData = new ArrayList<Labo>();
        String[] LaboName = getResources().getStringArray(R.array.doctor_names);
        String[] LaboLocation = getResources().getStringArray(R.array.doctor_place);
        String[] LaboContact = getResources().getStringArray(R.array.doctor_names);
        //TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        mRecyclerView = (RecyclerView) findViewById(R.id.RECYCLERView);

        ArrayList<Labo> linkedList = new ArrayList<Labo>();
        dbManagerLaboratoir = new DBManagerLaboratoir(this);
        dbManagerLaboratoir.open();
        linkedList = DBManagerLaboratoir.listLabo();
        LabosAdapter ada= new LabosAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Labo_activity.this));
        DBManagerLaboratoir.close();
    }
}
