package com.example.myapplication.Profiles;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.addProfile.AddDoctorFragment;
import com.example.myapplication.addProfile.AddHospitalFragment;
import com.example.myapplication.addProfile.AddLaboFragment;
import com.example.myapplication.addProfile.AddPharmacieFragment;
import com.example.myapplication.addProfile.addNormalUserFragment;

import static com.example.myapplication.utilities.PreferenceUtilities.getType;

public class ProfileTablayoutAdapter extends FragmentPagerAdapter {

    int mNumberOfFragment;
    Context myContext;

    public ProfileTablayoutAdapter(Context context, @NonNull FragmentManager fm, int NumberOfFragment) {
        super(fm);
        myContext = context;
        this.mNumberOfFragment = NumberOfFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new updateNormalUserFragment();
            case 1:
                String type = getType(myContext);
                if (type.equals("1")){
                    return new updateDoctorFragment();
                }else if (type.equals("2")) {
                    return new addNormalUserFragment();
                }else if (type.equals("3")){
                    return new updateHospitalFragment();
                }else if (type.equals("4")){
                    return new updateLaboFragment();
                }

            default:
                return null;}
    }

    @Nullable


    @Override
    public int getCount() {
        return mNumberOfFragment;    }
}
