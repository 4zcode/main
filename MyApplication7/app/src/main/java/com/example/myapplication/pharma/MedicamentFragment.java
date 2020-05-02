package com.example.myapplication.pharma;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MedicamentFragment extends Fragment {
    private MedicamentsAdapter mAdapter;
    private SearchView searchView;
    public MedicamentFragment(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicament, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.medicament_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = view.findViewById(R.id.search_medicament);
        ArrayList<Medicament> mMedicamentsData = new ArrayList<Medicament>();
        String[] medicamentName = getResources().getStringArray(R.array.doctor_names);
        String[] medicamentClass = getResources().getStringArray(R.array.doctor_sex);
        String[] medicamentPrix = getResources().getStringArray(R.array.doctor_place);
        for (int i = 0; i < medicamentName.length; i++) {
            mMedicamentsData.add(new Medicament("Medicament " + (i + 1), "Class i", "Prix: 200 DA"));
        }
        mAdapter = new MedicamentsAdapter(getActivity(), mMedicamentsData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });

    return view;
    }
}
