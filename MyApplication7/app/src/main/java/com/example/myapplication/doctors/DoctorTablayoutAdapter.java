package com.example.myapplication.doctors;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DoctorTablayoutAdapter extends FragmentPagerAdapter {

    int mNumberOfFragment;
    Context myContext;

    public DoctorTablayoutAdapter(Context context,@NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new SpecialisteDoctorsFragment();
            case 0:
                return new AdvanceSearchDoctorFragment();
            default:
                return null;}
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "Les Spécialités";
            case 0:
                return "Recherche Avancé";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfFragment;    }
}
