package com.example.myapplication.doctors.details;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.doctors.advanceSearch.AdvanceSearchDoctorFragment;
import com.example.myapplication.doctors.speciality.SpecialisteDoctorsFragment;
import com.example.myapplication.toolsbar.a_propos_de_nous.A_propos_de_nousFragment;

public class DetailsDoctorTablayoutAdapter extends FragmentPagerAdapter {

    int mNumberOfFragment;
    Context myContext;

    public DetailsDoctorTablayoutAdapter(Context context, @NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
               return new PersonalInformationDoctorFragment();
            case 1:
                return new AddresseDoctorFragment();
            default:
                return null;
        }
    }

    @Nullable


    @Override
    public int getCount() {
        return mNumberOfFragment;    }
}
