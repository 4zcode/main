package com.example.myapplication.toolsbar.don_de_sang;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.toolsbar.a_propos_de_nous.A_propos_de_nousFragment;

public class donateurTablayoutAdapter extends FragmentStatePagerAdapter {
    int mNumberOfFragment;
    Context myContext;
    public donateurTablayoutAdapter(Context context, @NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Blooddonor();
            case 1:
               return new findadonor();
            default:
                return null;}
    }


    @Override
    public int getCount() {
        return mNumberOfFragment;    }

}

