package com.example.myapplication.addProfile;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import static com.example.myapplication.utilities.PreferenceUtilities.getLastViewPagerInscriptionPosition;

public class AddUtilisateurActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_utilisateur);
        tabLayout = (TabLayout) findViewById(R.id.insertion_tablayout);
        viewPager = (ViewPager) findViewById(R.id.insetion_viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        insertionTablayoutAdapter myAdapter = new insertionTablayoutAdapter(this, getSupportFragmentManager(),5);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);


        int p =  getLastViewPagerInscriptionPosition(getBaseContext());
            viewPager.setCurrentItem(p);


        //
        tabLayout.getTabAt(0).setIcon(R.drawable.user1);
      //  tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(1).setIcon(R.drawable.medcin_icon);
     //   tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(2).setIcon(R.drawable.pharmacy_icon);
        //  tabLayout.getTabAt(3).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(3).setIcon(R.drawable.hopital_icon);
      //  tabLayout.getTabAt(2).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);


        tabLayout.getTabAt(4).setIcon(R.drawable.labo_icon);
     //   tabLayout.getTabAt(4).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               // tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               // tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
           //  super.onPageSelected(position);
            int p =  getLastViewPagerInscriptionPosition(getBaseContext());
            if (position !=p ){
            viewPager.setCurrentItem(p);
            Toast.makeText(getBaseContext(),"Vous dever completer cette page d'abord",Toast.LENGTH_LONG).show();
        }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    });

    }
}
