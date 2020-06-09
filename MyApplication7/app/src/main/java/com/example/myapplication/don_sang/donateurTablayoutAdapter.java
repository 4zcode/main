package com.example.myapplication.don_sang;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class donateurTablayoutAdapter extends FragmentPagerAdapter {
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Blood Donor";
            case 1:
                return "Find a Donor";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfFragment;    }

}

