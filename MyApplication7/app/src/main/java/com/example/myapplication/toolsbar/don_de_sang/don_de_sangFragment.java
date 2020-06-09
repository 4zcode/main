package com.example.myapplication.toolsbar.don_de_sang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.addProfile.addDonation;
import com.example.myapplication.don_sang.donateurTablayoutAdapter;
import com.example.myapplication.don_sang.donateuractivity;
import com.google.android.material.tabs.TabLayout;

public class don_de_sangFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_de_sang, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout1donateur);
        Log.d("donateur","we are in activity1");
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerdonateur);
        Log.d("donateur","we are in activity1");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        donateurTablayoutAdapter myAdapter = new donateurTablayoutAdapter(getContext(), getActivity().getSupportFragmentManager(), 2);
        Log.d("donateur","we are in activity1");
        viewPager.setAdapter(myAdapter);
        Log.d("donateur","we are in activity1");
        tabLayout.setupWithViewPager(viewPager);
        Log.d("donateur","we are in activity1");
  return view;
    }

}