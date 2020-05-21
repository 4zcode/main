package com.example.myapplication.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.addProfile.Insertion;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    public EditText username;
    public EditText pass, repeatPass;
   public FirebaseAuth firebaseAuth;
   private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=(EditText) findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.password);
        repeatPass = (EditText)findViewById(R.id.repeat_password);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void login(View view) {
        final String user=username.getText().toString().trim();
        String pass=this.pass.getText().toString();
        String passRepeat = this.repeatPass.getText().toString();
        if (TextUtils.isEmpty(user)|| TextUtils.isEmpty(pass) || TextUtils.isEmpty(passRepeat)){
            Toast.makeText(this,"un champ est vide",Toast.LENGTH_LONG).show();
        }else if (! pass.equals(passRepeat)){
            Toast.makeText(this,"Vous devrez revérifier votre mot de pass",Toast.LENGTH_LONG).show();
        } else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Creation de compte");
            progressDialog.setMessage("S'il vous plait attendez un peu ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"sign in with sucess",Toast.LENGTH_LONG).show();
                        FirebaseUser user1=firebaseAuth.getCurrentUser();
                        user1.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>(){
                            public void onComplete( Task<Void> task){
                                if(task.isSuccessful()){Toast.makeText(getApplicationContext(),"Vérifiez votre boit mail",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Insertion.class);
                                    startActivity(intent);
                                }else{
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }else{
                        String error=task.getException().getMessage();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }

}
