package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.doctors.Doctors;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class chatRoom extends AppCompatActivity {
    private TextView textView;
    private ScrollView scrollView;
    private Button button;
    private EditText editText;
    private DatabaseReference firebaseDatabase;
    private FirebaseUser user1;
    private Map<String,Object> user,senderUser;
    private String ID_reciver,MessageRecever = "";
    private SharedPreferences myPef ;
    private ProgressDialog mProgressDialog;
    private Handler handler;
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Message");
    private Thread thread;
    private interface FireBaseCallBack {
        void onCallBack(String message);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        textView =(TextView) findViewById(R.id.text_view_messages);
        button =(Button) findViewById(R.id.envoyer_button);
        editText = (EditText) findViewById(R.id.edit_text_envoyer);
        scrollView = (ScrollView) findViewById(R.id.scroll_chat);
        ID_reciver = getIntent().getStringExtra(Doctors.RECIVER);
         handler= new Handler();
        user= new HashMap<String,Object>();
        senderUser = new HashMap<String,Object>();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Message");
        user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        thread = new Thread(){
          @Override
          public void run() {
           try {
               while (!thread.isInterrupted()) {
                   Thread.sleep(1000);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Afficher();
                       }
                   });
               }
           }catch (InterruptedException e){e.printStackTrace();
           }
          }
    };

         thread.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        },4000);    }
    public Spanned getSpannedText(String text){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            return Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT);
        }else{ return Html.fromHtml(text);}
    }
    public void Afficher(){
        readData(new FireBaseCallBack() {
            @Override
            public void onCallBack(String msg) {

            }
        });
    }
    public void readData(FireBaseCallBack fireBaseCallBack) {
        DoctorsRef.child("RECEVER").child(user1.getUid()).child(ID_reciver).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String msg = dataSnapshot.child("message_afficher").getValue(String.class);
                    MessageRecever=msg;
                textView.setText(getSpannedText(msg));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("chatdd", databaseError.getMessage());
            }
        });
    }

    public void Envoyer(View view) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        String message = editText.getText().toString();
        myPef = getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String name_current_user = myPef.getString("userName","annonyme");
        user.put("ID_Reciver",ID_reciver);
        user.put("message_envoyer",name_current_user+": "+message+"<br>");
        firebaseDatabase.child("ENVOYE").child(user1.getUid()).child(ID_reciver).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"le message est envoyé",Toast.LENGTH_LONG).show();

                }else{
                    String error;
                    error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

                }
            }
        });
        String allMSG = MessageRecever + "<br>" + "  <b>"+ name_current_user+"</b> "+": "+message+"<br>";
        senderUser.put("message_afficher",allMSG);
        firebaseDatabase.child("RECEVER").child(user1.getUid()).child(ID_reciver).setValue(senderUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"le message est envoyé",Toast.LENGTH_LONG).show();

                }else{
                    String error;
                    error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

                }
            }
        });
        firebaseDatabase.child("RECEVER").child(ID_reciver).child(user1.getUid()).setValue(senderUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                }else{
                    String error;
                    error=task.getException().getMessage();
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

                }
            }
        });
        mProgressDialog.dismiss();
        editText.getText().clear();
        InputMethodManager imm = (InputMethodManager) chatRoom.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view1 = chatRoom.this.getCurrentFocus();
        if(view1 == null){
            view=new View(chatRoom.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        Afficher();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        },1000);

    }

}
