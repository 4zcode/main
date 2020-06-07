package com.example.myapplication.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.message.DBManagerMessage;
import com.example.myapplication.utilities.PreferenceUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.myapplication.utilities.PreferenceUtilities.KEY_IS_LOGIN;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_TYPE;

public class Signin extends AppCompatActivity {
    private EditText username;
    private EditText pass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private SharedPreferences myPef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        username=(EditText) findViewById(R.id.AddressEmail);
        pass=(EditText)findViewById(R.id.Motdepasse);
        firebaseAuth=FirebaseAuth.getInstance();
        myPef =getSharedPreferences("userPref", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
        progressDialog.setIndeterminate(true);
    }

    public void signin(View view) {
        String user=username.getText().toString().trim();
        String pass=this.pass.getText().toString();
        if (TextUtils.isEmpty(user)|| TextUtils.isEmpty(pass)){
            Toast.makeText(this,"error",Toast.LENGTH_LONG).show();
        }else{
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        PreferenceUtilities.saveUserInfo(getBaseContext(),true);
                        SharedPreferences.Editor editor = myPef.edit();
                        editor.putBoolean(KEY_IS_LOGIN,true);
                        editor.apply();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Connectez-vous avec succès",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else{
                        progressDialog.dismiss();
                         String error=task.getException().getMessage();
                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    public void signUp(View view) {
            Intent intent=new Intent(this, SignupActivity.class);
            startActivity(intent);
    }


    public void passwordOublie(View view) {
        final String user=username.getText().toString().trim();
        if (TextUtils.isEmpty(user)){
            Toast.makeText(this,"remplir votre mail",Toast.LENGTH_LONG).show();
        }else {
            AlertDialog message = new AlertDialog.Builder(this)
                    .setTitle("Avertissement")
                    .setMessage("Voullez vous vraiment changer votre mot de pass?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.show();
                            firebaseAuth.sendPasswordResetEmail(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getBaseContext(),"vréfier votre mail",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }else {
                                        Toast.makeText(getBaseContext(),"echec d'envoyer le mot de passe",Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                    }
                                }
                            });

                        }
                    })
                    .setNegativeButton("Non", null)
                    .setIcon(R.drawable.sign1)
                    .show();


        }
    }
}
