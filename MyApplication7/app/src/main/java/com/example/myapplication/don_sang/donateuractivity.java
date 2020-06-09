package com.example.myapplication.don_sang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class donateuractivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("donateur","we are in activity1");
        super.onCreate(savedInstanceState);
        Log.d("donateur","we are in activity1");
        setContentView(R.layout.activity_donateuractivity);
        Log.d("donateur","we are in activity1");
        tabLayout = (TabLayout) findViewById(R.id.tablayout1donateur);
        Log.d("donateur","we are in activity1");
        viewPager = (ViewPager) findViewById(R.id.viewpagerdonateur);
        Log.d("donateur","we are in activity1");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        donateurTablayoutAdapter myAdapter = new donateurTablayoutAdapter(this, getSupportFragmentManager(), 2);
        Log.d("donateur","we are in activity1");
        viewPager.setAdapter(myAdapter);
        Log.d("donateur","we are in activity1");
        tabLayout.setupWithViewPager(viewPager);
        Log.d("donateur","we are in activity1");

    }
}