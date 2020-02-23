package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

public class hospital_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_activity);
        RecyclerView mRecyclerView;
        final HopitalsAdapter mAdapter;
        final ArrayList<Hopital> mSportsData = new ArrayList<Hopital>();
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        String[] sportsCate = getResources().getStringArray(R.array.sports_cate);
        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Hopital(sportsList[i], sportsInfo[i], sportsCate[i],
                    sportsImageResources.getResourceId(i, 0)));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new HopitalsAdapter(this, mSportsData);

        mRecyclerView.setAdapter(mAdapter);
    }
}
