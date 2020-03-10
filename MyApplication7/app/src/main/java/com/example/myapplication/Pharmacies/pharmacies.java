package com.example.myapplication.Pharmacies;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DBManagerPharmacie;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class pharmacies extends AppCompatActivity {
    private DBManagerPharmacie dbManagerPharmacie;
    private DatabaseHelper mDatabase;
    private LinkedList<pharmaciesinit> linkedList;
    adapterpharmacies ada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("pharma","here is the prblm 0");

        setContentView(R.layout.activity_pharmacies);
        Log.d("pharma","here is the prblm 1");

        ArrayList<pharmaciesinit> linkedList = new ArrayList<pharmaciesinit>();
        Log.d("pharma","here is the prblm 2");

      /*  String[] thename =getResources().getStringArray(R.array.thename);
        String[] theadress=getResources().getStringArray(R.array.theadress);
        String[] oppen =getResources().getStringArray(R.array.oppen);
        String[] close =getResources().getStringArray(R.array.close);*/

        int counter;

        RecyclerView mreclview;
        Log.d("pharma","here is the prblm 3");

        dbManagerPharmacie = new DBManagerPharmacie(this);
        Log.d("pharma","here is the prblm 4");

        dbManagerPharmacie.open();
        mDatabase = new DatabaseHelper(this);
        linkedList = mDatabase.listPharmacies();
        Log.d("pharma","here is the prblm 2");

       /* for (counter=0;counter<=4;counter++) {
            linkedList.add(new pharmaciesinit(thename[counter],theadress[counter],oppen[counter],close[counter]));
        }*/
        mreclview=(RecyclerView) findViewById(R.id.recycle);

        ada=new adapterpharmacies(this,linkedList);
        mreclview.setAdapter(ada);
        mreclview.setLayoutManager(new LinearLayoutManager(pharmacies.this));
        dbManagerPharmacie.close();
    }
}
