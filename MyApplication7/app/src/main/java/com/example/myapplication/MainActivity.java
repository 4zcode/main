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

    public void med(View view) {
        Intent intent3 = new Intent(this,medicament_activity.class);
        startActivity(intent3);
    }

    public void doc(View view) {
        Intent intent2 = new Intent(this,DoctorActivity.class);
        startActivity(intent2);
    }

    public void pharma(View view) {
        Intent intent = new Intent(this,pharmacies.class);
        startActivity(intent);
    }

    public void hos(View view) {
        Intent intent4 = new Intent(this, HopitalActivity.class);
        startActivity(intent4);
    }
}
