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

    public void addc (View view) {
        Intent intent=new Intent(this,insertdoct.class );
        startActivity(intent);
    }

    public void addlabo(View view) {
        Intent intent=new Intent(this,Add_labo.class );
        startActivity(intent);
    }
}
