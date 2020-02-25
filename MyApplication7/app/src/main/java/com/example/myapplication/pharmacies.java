package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedList;

public class pharmacies extends AppCompatActivity {
    private LinkedList<pharmaciesinit> linkedList;
    adapterpharmacies ada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);
        final ArrayList<pharmaciesinit> linkedList = new ArrayList<pharmaciesinit>();
        String[] thename =getResources().getStringArray(R.array.thename);
        String[] theadress=getResources().getStringArray(R.array.theadress);
        String[] oppen =getResources().getStringArray(R.array.oppen);
        String[] close =getResources().getStringArray(R.array.close);

        int counter;
        RecyclerView mreclview;

        for (counter=0;counter<=4;counter++) {
            linkedList.add(new pharmaciesinit(thename[counter],theadress[counter],oppen[counter],close[counter]));
        }
        mreclview=(RecyclerView) findViewById(R.id.recycle);

        ada=new adapterpharmacies(this,linkedList);
        mreclview.setAdapter(ada);
        mreclview.setLayoutManager(new LinearLayoutManager(pharmacies.this));
    }
}
