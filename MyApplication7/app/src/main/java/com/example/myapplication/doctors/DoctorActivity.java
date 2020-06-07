package com.example.myapplication.doctors;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class DoctorActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
      //
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_list_black_24dp);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_people_outline);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        ViewPager tabHost = (ViewPager) findViewById(R.id.dviewpager);
        if (tabHost.getCurrentItem() == 0) super.onBackPressed();
       else tabHost.setCurrentItem(tabHost.getCurrentItem()-1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.open_enter,R.anim.nav_default_exit_anim);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if (id == R.id.nav_home){
           onBackPressed();
       }

        return super.onOptionsItemSelected(item);
    }
}
