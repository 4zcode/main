package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Pharmacies.DBManagerph;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
    public void addph(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        EditText name=(EditText) findViewById(R.id.name);
        EditText adrs=(EditText) findViewById(R.id.adrs);
        EditText open=(EditText) findViewById(R.id.open);
        EditText close=(EditText) findViewById(R.id.close);
        DBManagerph dbManagerPharmacie = new DBManagerph(this);
        dbManagerPharmacie.open();
        dbManagerPharmacie.insert(name.getText().toString(),adrs.getText().toString(),open.getText().toString(),close.getText().toString());
        dbManagerPharmacie.close();
    }
}
