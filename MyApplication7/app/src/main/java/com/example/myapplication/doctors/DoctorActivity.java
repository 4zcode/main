package com.example.myapplication.doctors;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.doctors.DoctorTablayoutAdapter;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class DoctorActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        tabLayout = (TabLayout) findViewById(R.id.tablayout1);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        DoctorTablayoutAdapter myAdapter = new DoctorTablayoutAdapter(this, getSupportFragmentManager(),2);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
