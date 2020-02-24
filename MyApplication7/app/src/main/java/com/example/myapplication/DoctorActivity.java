package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class DoctorActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("akram","we stil alive 1");
        super.onCreate(savedInstanceState);
        Log.d("akram","we stil alive 2");

        setContentView(R.layout.activity_doctor);
        Log.d("akram","we stil alive 3");

        tabLayout = (TabLayout) findViewById(R.id.tablayout1);
        Log.d("akram","we stil alive 4");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Log.d("akram","we stil alive 5");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Log.d("akram","we stil alive 6");

        DoctorTablayoutAdapter myAdapter = new DoctorTablayoutAdapter(this, getSupportFragmentManager(),2);
        Log.d("akram","we stil alive 7");

        viewPager.setAdapter(myAdapter);
        Log.d("akram","we stil alive 8");

        tabLayout.setupWithViewPager(viewPager);
        Log.d("akram","we stil alive 9");

    }
}
