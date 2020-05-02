package com.example.myapplication.pharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.doctors.DoctorTablayoutAdapter;
import com.google.android.material.tabs.TabLayout;

public class pharmaactivty extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    public String wilaya;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmaactivty);
        tabLayout = (TabLayout) findViewById(R.id.tablayout1ph);
        viewPager = (ViewPager) findViewById(R.id.viewpagerph);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pharmacyTablayoutAdapter myAdapter = new pharmacyTablayoutAdapter(this, getSupportFragmentManager(),2);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
        spinner=(Spinner)findViewById(R.id.docspinner);
        if(spinner!=null){spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) pharmaactivty.this);}
        arrayAdapter=ArrayAdapter.createFromResource(pharmaactivty.this, R.array.wilaya,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(arrayAdapter);
        }

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        wilaya=parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
