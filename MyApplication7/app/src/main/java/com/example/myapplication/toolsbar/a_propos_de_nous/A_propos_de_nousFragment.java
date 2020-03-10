package com.example.myapplication.toolsbar.a_propos_de_nous;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class A_propos_de_nousFragment extends Fragment {

    private A_propos_de_nousModel aproposdenousModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aproposdenousModel =
                ViewModelProviders.of(this).get(A_propos_de_nousModel.class);
        View root = inflater.inflate(R.layout.fragment_don_de_sang, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        aproposdenousModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}