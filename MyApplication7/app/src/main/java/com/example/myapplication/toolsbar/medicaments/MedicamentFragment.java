package com.example.myapplication.toolsbar.medicaments;

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
    ArrayList<Medicament> mMedicamentsData = new ArrayList<Medicament>();



    public MedicamentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =inflater.inflate(R.layout.fragment_medicament, container, false);
        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.RecycleView1);
        searchView = root.findViewById(R.id.search_medicament);
        mMedicamentsData.add(new Medicament("Medicament 1","200 DA","I"));
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



        return root;
    }
}
