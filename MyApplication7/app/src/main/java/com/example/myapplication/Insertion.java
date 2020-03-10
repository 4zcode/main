package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Insertion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insersion);
    }
    public void insererpharma(View view){
        Intent intent=new Intent(this, Add.class);
        startActivity(intent);
    }
    public void insererhopital(View view){
        Intent intent=new Intent(this, AddHospital.class);
        startActivity(intent);
    }
}
