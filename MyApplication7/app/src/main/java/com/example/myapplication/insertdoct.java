package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.doctors.DBManagerDoctor;

public class insertdoct extends AppCompatActivity {

    public  EditText name;
    public  EditText adress;
    public  EditText phone;
    public  EditText specailety;
    public  RadioGroup sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdoct);
        Log.d("aymane","we stiil a life6");
        name=(EditText) findViewById(R.id.namedc);
        adress=(EditText) findViewById(R.id.adressdc);
        phone=(EditText) findViewById(R.id.phonedc);
        specailety=(EditText) findViewById(R.id.speclitedc);
        sex=(RadioGroup) findViewById(R.id.sex);
    }

    public void adddoctor(View view) {
        String name =this.name.getText().toString();
        String adress =this.adress.getText().toString();
        String spec =this.specailety.getText().toString();
        String phone=this.phone.getText().toString();
        String sex;
        DBManagerDoctor db= new DBManagerDoctor(this);
        db.open();
        int i=this.sex.getCheckedRadioButtonId();
        if(i== R.id.man){sex= "man";}else{sex="woman";}
        db.insert(name,adress,phone,spec,sex);
        Log.d("aymane","we stiil a life7");
        db.close();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
