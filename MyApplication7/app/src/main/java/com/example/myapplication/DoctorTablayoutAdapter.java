package com.example.myapplication;

import android.content.Context;
import android.util.Log;

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
        Log.d("akram","we stil alive 40");

        myContext = context;
        Log.d("akram","we stil alive 41");

        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Log.d("akram","we stil alive 42");

                return new SpecialisteDoctorsFragment();
            case 1:
                Log.d("akram","we stil alive 43");

                return new AdvanceSearchDoctorFragment();
            default:
                return null;}
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                Log.d("akram","we stil alive 44");

                return "Les Spécialités";
            case 1:
                Log.d("akram","we stil alive 45");

                return "Recherche Avancé";
            default:
                Log.d("akram","we stil alive 46");

                return null;
        }
    }

    @Override
    public int getCount() {
        Log.d("akram","we stil alive 47");

        return mNumberOfFragment;    }
}
