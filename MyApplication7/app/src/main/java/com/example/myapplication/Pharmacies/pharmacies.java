package com.example.myapplication.Pharmacies;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class pharmacies extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private LinkedList<pharmaciesinit> linkedList;
    public Spinner spinner ;
    public adapterpharmacies ada;
    public String wilaya ;
    ArrayAdapter<CharSequence> adapspin;
    private phbased mDB;
    RecyclerView mreclview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);
        ArrayList<pharmaciesinit> linkedList = new ArrayList<pharmaciesinit>();
        mDB=new phbased(pharmacies.this);
        for(int i=0;i<=19;i++){mDB.insert("akram"+i,"labita","8","12");}
        linkedList=mDB.pharlist();

       /* String[] thename =getResources().getStringArray(R.array.thename);
        String[] theadress=getResources().getStringArray(R.array.theadress);
        String[] oppen =getResources().getStringArray(R.array.oppen);
        String[] close =getResources().getStringArray(R.array.close);

        int counter;
        RecyclerView mreclview;

        for (counter=0;counter<=4;counter++) {
            linkedList.add(new pharmaciesinit(thename[counter],theadress[counter],oppen[counter],close[counter]));
        }
        */
         mreclview=(RecyclerView) findViewById(R.id.recycle);
        ada=new adapterpharmacies(this,linkedList);
        mreclview.setAdapter(ada);
        mreclview.setLayoutManager(new LinearLayoutManager(pharmacies.this));
        spinner=(Spinner) findViewById(R.id.phspinner);
        if(spinner!=null){spinner.setOnItemSelectedListener(pharmacies.this);}
        adapspin=ArrayAdapter.createFromResource(pharmacies.this,R.array.wilaya,android.R.layout.simple_spinner_item);
        adapspin.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapspin);
        }

    }

    public void Searchph(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        wilaya=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
