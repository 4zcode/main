package com.example.myapplication.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.utilities.PreferenceUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.myapplication.utilities.PreferenceUtilities.KEY_IS_LOGIN;

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
        username=(EditText) findViewById(R.id.usernamein);
        pass=(EditText)findViewById(R.id.passwordin);
        firebaseAuth=FirebaseAuth.getInstance();
        myPef =getSharedPreferences("userPref", Context.MODE_PRIVATE);
    }

    public void signin(View view) {
        String user=username.getText().toString().trim();
        String pass=this.pass.getText().toString();
        if (TextUtils.isEmpty(user)|TextUtils.isEmpty(pass)){
            Toast.makeText(this,"error",Toast.LENGTH_LONG).show();
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        SharedPreferences.Editor editor = myPef.edit();
                        editor.putBoolean(KEY_IS_LOGIN,true);
                        editor.apply();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"sign in with sucess",Toast.LENGTH_LONG).show();
                        PreferenceUtilities.saveUserInfo(getBaseContext(),FirebaseAuth.getInstance().getCurrentUser() != null);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
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

    public void returnToMainActivity(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
