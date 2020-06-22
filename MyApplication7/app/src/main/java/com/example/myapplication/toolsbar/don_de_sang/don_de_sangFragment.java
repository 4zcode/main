package com.example.myapplication.toolsbar.don_de_sang;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class don_de_sangFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_de_sang, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout1donateur);
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerdonateur);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        donateurTablayoutAdapter myAdapter = new donateurTablayoutAdapter(getContext(), getActivity().getSupportFragmentManager(), 2);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_add_black_24dp);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_search_black_24dp);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}