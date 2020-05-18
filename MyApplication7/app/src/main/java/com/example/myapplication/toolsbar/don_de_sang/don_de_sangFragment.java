package com.example.myapplication.toolsbar.don_de_sang;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.addProfile.addDonation;
import com.example.myapplication.don_sang.donateuractivity;

public class don_de_sangFragment extends Fragment {

    private don_de_sangViewModel dondesangViewModel;
    private Button add;
    private Button show;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dondesangViewModel =
                ViewModelProviders.of(this).get(don_de_sangViewModel.class);
        View root = inflater.inflate(R.layout.fragment_don_de_sang, container, false);
        add=root.findViewById(R.id.add);
        show=root.findViewById(R.id.show);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDonation();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donateuractivity();
            }
        });
        return root;
    }
    public void addDonation(){
        Intent intent =new Intent(getActivity(), addDonation.class);
        startActivity(intent);
    }
    public void donateuractivity(){
        Intent intent =new Intent(getActivity(), donateuractivity.class);
        startActivity(intent);
    }

}