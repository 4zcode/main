package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lunchhopitals(View view) {
        Intent hopitalsIntent =new Intent(MainActivity.this,hospital_activity.class);
        startActivity(hopitalsIntent);
    }
}
