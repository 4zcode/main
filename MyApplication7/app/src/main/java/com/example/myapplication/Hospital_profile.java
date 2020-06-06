package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Hospital_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_profile);

        TextView phonview = findViewById(R.id.phon);
        TextView servview = findViewById(R.id.serv);
        TextView descview = findViewById(R.id.desc);

        servview.setText(getIntent().getStringExtra("SERV_KEY"));
        phonview.setText(getIntent().getStringExtra("PHON_KEY"));
        descview.setText(getIntent().getStringExtra("DESC_KEY"));
        getActionBar().setTitle(getIntent().getStringExtra("NAME_KEY"));
    }
}
