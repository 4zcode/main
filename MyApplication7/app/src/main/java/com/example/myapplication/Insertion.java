package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Insertion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("aymane","we still dead1");
        setContentView(R.layout.activity_insersion);
        Log.d("aymane","we still dead2");
    }
    public void insererpharma(View view){
        Intent intent=new Intent(this, Add.class);
        startActivity(intent);
    }
    public void insererhopital(View view){
        Intent intent=new Intent(this, AddHospital.class);
        startActivity(intent);
    }

    public void addc (View view) {
        Log.d("aymane","we still dead3");
        Intent intent=new Intent(this,insertdoct.class );
        Log.d("aymane","we still dead4");
        startActivity(intent);
        Log.d("aymane","we still dead5");
    }

    public void addlabo(View view) {
    }
}
