package com.example.myapplication.pharma;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class pharmacyTablayoutAdapter  extends FragmentPagerAdapter {
    int mNumberOfFragment;
    Context myContext;
    public pharmacyTablayoutAdapter(Context context, @NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Advancedsearchpahraciesactivity();
            case 1:
                return new MedicamentFragment();
            default:
                return null;}
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Les pharmacies";
            case 1:
                return "les medicamentes";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfFragment;    }

}
