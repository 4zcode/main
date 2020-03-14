package com.example.myapplication.toolsbar.don_de_sang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class don_de_sangFragment extends Fragment {

    private don_de_sangViewModel dondesangViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dondesangViewModel =
                ViewModelProviders.of(this).get(don_de_sangViewModel.class);
        View root = inflater.inflate(R.layout.fragment_a_proos_de_nous, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        dondesangViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}