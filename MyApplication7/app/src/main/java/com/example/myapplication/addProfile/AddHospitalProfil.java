package com.example.myapplication.addProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Hospital.DBManagerHospital;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class AddHospitalProfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);
    }
    public void addhos(View view){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        EditText name=(EditText) findViewById(R.id.namehos);
        EditText place=(EditText) findViewById(R.id.placehos);
        EditText number=(EditText) findViewById(R.id.numberhos);
        DBManagerHospital dbManagerHospital = new DBManagerHospital(this);
        dbManagerHospital.open();
        dbManagerHospital.insert(name.getText().toString(),place.getText().toString(),number.getText().toString());
        dbManagerHospital.close();
    }
}
