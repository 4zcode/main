package com.example.myapplication.addProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class Insertion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insersion);
    }
    public void insererpharma(View view){
        Intent intent=new Intent(this, AddPharmacyProfil.class);
        startActivity(intent);
    }
    public void insererhopital(View view){
        Intent intent=new Intent(this, AddHospitalProfil.class);
        startActivity(intent);
    }

    public void addc (View view) {
        Intent intent=new Intent(this,AddDoctorProfile.class );
        startActivity(intent);
    }

    public void addlabo(View view) {
        Intent intent=new Intent(this,AddLaboProfile.class );
        startActivity(intent);
    }

}
