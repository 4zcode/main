package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.myapplication.Laboratoir.DBManagerLaboratoir;

public class Add_labo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labo);
        EditText name=(EditText) findViewById(R.id.namelabo);
        EditText place=(EditText) findViewById(R.id.placelabo);
        EditText number=(EditText) findViewById(R.id.numberlabo);
        DBManagerLaboratoir dbManagerLaboratoir = new DBManagerLaboratoir(this);
        dbManagerLaboratoir.open();
        dbManagerLaboratoir.insert(name.getText().toString(),place.getText().toString(),number.getText().toString());
        dbManagerLaboratoir.close();
    }
}
