package com.example.myapplication.doctors;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class DoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TabLayout tabLayout;
    ViewPager viewPager;
    public String wilaya;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        tabLayout = (TabLayout) findViewById(R.id.tablayout1);
        viewPager = (ViewPager) findViewById(R.id.dviewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        DoctorTablayoutAdapter myAdapter = new DoctorTablayoutAdapter(this, getSupportFragmentManager(),2);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);
        spinner=(Spinner)findViewById(R.id.docspinner);
        if(spinner!=null){spinner.setOnItemSelectedListener(DoctorActivity.this);}
        arrayAdapter=ArrayAdapter.createFromResource(DoctorActivity.this, R.array.wilaya,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(arrayAdapter);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          wilaya=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
