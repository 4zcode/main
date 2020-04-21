package com.example.myapplication.Laboratoir;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
