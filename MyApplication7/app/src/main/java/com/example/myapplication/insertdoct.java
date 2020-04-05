package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.doctors.DBManagerDoctor;
import com.example.myapplication.doctors.Doctors;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        int i=this.sex.getCheckedRadioButtonId();
        if(i== R.id.man){sex= "man";}else{sex="woman";}
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        DatabaseReference firebaseDatabase;
        firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("Doctor");
        FirebaseUser user1=FirebaseAuth.getInstance().getInstance().getCurrentUser();
        Map<String,Object> user= new HashMap<String,Object>();
        user.put("NameDoctor",name);
        user.put("PlaceDoctor",adress);
        user.put("phone",phone);
        user.put("spec",spec);
        user.put("SexDoctor",sex);
        user.put("Doctor_ID_Firebase",user1.getUid());

        //Doctors doc = new Doctors(name,adress,phone,spec,sex);
        firebaseDatabase.child(user1.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"your information is update",Toast.LENGTH_LONG).show();

                }else{
                    String error;
                    error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
