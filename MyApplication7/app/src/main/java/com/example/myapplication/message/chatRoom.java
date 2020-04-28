package com.example.myapplication.message;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.myapplication.R;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;

public class chatRoom extends AppCompatActivity {
    public final static String TAG = chatRoom.class.getSimpleName();


    private TextView textView;
    private ScrollView scrollView;
    private Button button;
    private EditText editText;
    private DatabaseReference firebaseDatabase;
    private FirebaseUser user1;
    private Map<String,Object> user,senderUser;
    private String ID_reciver,MessageRecever = "",SenderName, ReceiverImage;
    private SharedPreferences myPef ;
    private ProgressDialog mProgressDialog;
    private Handler handler;
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Message");
    private Thread thread;

    public void resize(View view) {
        ScrollDown(1000);
        Log.d("msgPrblmTest","clickable");

    }

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
        ReceiverImage = getIntent().getStringExtra(Doctors.RECIVER_IMAGE);
        SenderName =getIntent().getStringExtra(Doctors.SENDER);
        handler= new Handler();
        user= new HashMap<String,Object>();
        senderUser = new HashMap<String,Object>();
        this.setTitle(SenderName);
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Message");
        user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        myPef = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        updateAffichage(1000);
        ScrollDown(3000);
    }
    public void updateAffichage(final int duree){
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(duree);
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
    }

    public Spanned getSpannedText(String text){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            return Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT);
        }else{ return Html.fromHtml(text);}
    }
    public void Afficher(){
        getMessageReceiver();
        readData(new FireBaseCallBack() {
            @Override
            public void onCallBack(String msg) {

            }
        });
    }
    public void readData(FireBaseCallBack fireBaseCallBack) {
        if (!ID_reciver.equals(user1.getUid())) {
            DoctorsRef.child("RECEVER").child(user1.getUid()).child(ID_reciver).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String msg = dataSnapshot.child("message_afficher").getValue(String.class);
                        MessageRecever = msg;
                        String msgAfficher= msg.replaceAll(ID_reciver,SenderName);
                      String  msgAfficher2=  msgAfficher.replaceAll( user1.getUid(),myPef.getString("userName","Annonyme"));
                        textView.setText(getSpannedText(msgAfficher2));
                    } else textView.setText("");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, databaseError.getMessage());
                }
            });
        }
    }

    public void Envoyer(View view) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        String message = editText.getText().toString().trim();
        if(!message.isEmpty()) {
            String name_current_user = myPef.getString("userName", "Annonyme");
            DateFormat date = new SimpleDateFormat("d MMM yyyy, HH:mm:SS");
            String dt = date.format(Calendar.getInstance().getTime());
            updateUserReceiver(message, SenderName, ID_reciver, dt);
            updateReceiverUser(name_current_user, message, ID_reciver, dt);
            String allMSG = MessageRecever + "<br>" + "<b>" + user1.getUid() + "</b>" + " : <br> " + message + "<br> ";
            updateAllMsg(allMSG, ID_reciver);
            mProgressDialog.dismiss();
            editText.getText().clear();
            hideKeyBoard(view);
            Afficher();
            ScrollDown(1000);
        }else             mProgressDialog.dismiss();

    }
   public void updateUserReceiver(String message,String senderName,String ID_reciver,String date){
        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", ID_reciver);
            user.put("Sender_Name", senderName);
            user.put("Sender_Image", ReceiverImage);
            user.put("message_envoyer", "Moi: " + message);
            user.put("Is_Readed", "true");
            user.put("Date", date);
            firebaseDatabase.child("ENVOYE").child(user1.getUid()).child(ID_reciver).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "le message est envoyé", Toast.LENGTH_LONG).show();

                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
   }
   public void updateReceiverUser(String name_current_user,String message,String ID_reciver,String date){
        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", user1.getUid());
            user.put("Sender_Name", name_current_user);
            user.put("Sender_Image", myPef.getString(KEY_USER_IMAGE,DEFAULT_USER_IMAGE));
            user.put("message_envoyer", message);
            user.put("Is_Readed", "false");
            user.put("Date", date);
            firebaseDatabase.child("ENVOYE").child(ID_reciver).child(user1.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "le message est envoyé", Toast.LENGTH_LONG).show();

                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
   }
   public void updateAllMsg(String allMSG,String ID_reciver){
        if (!ID_reciver.equals(user1.getUid())) {
            senderUser.put("message_afficher", allMSG);
            firebaseDatabase.child("RECEVER").child(user1.getUid()).child(ID_reciver).setValue(senderUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "le message est envoyé", Toast.LENGTH_LONG).show();

                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    }
                }
            });
            firebaseDatabase.child("RECEVER").child(ID_reciver).child(user1.getUid()).setValue(senderUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
   }
   public void hideKeyBoard(View view){
       InputMethodManager imm = (InputMethodManager) chatRoom.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
       View view1 = chatRoom.this.getCurrentFocus();
       if(view1 == null){
           view=new View(chatRoom.this);
       }
       imm.hideSoftInputFromWindow(view.getWindowToken(),0);
   }
   public void ScrollDown(int duree){
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               scrollView.fullScroll(View.FOCUS_DOWN);
           }
       },duree);
   }
   public void updateIs_ReadedStatus(String message,String senderName,String ID_reciver,String date){
        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", ID_reciver);
            user.put("Sender_Name", senderName);
            user.put("Sender_Image", ReceiverImage);
            user.put("message_envoyer", message);
            user.put("Is_Readed", "true");
            user.put("Date", date);
            firebaseDatabase.child("ENVOYE").child(user1.getUid()).child(ID_reciver).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "update is read is the problem");
                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
   }
   public void getMessageReceiver(){
       DoctorsRef.child("ENVOYE").child(user1.getUid()).child(ID_reciver).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               boolean is_exist = dataSnapshot.child("message_envoyer").exists() && dataSnapshot.child("Date").exists();
               if (is_exist) {
                   String msg = dataSnapshot.child("message_envoyer").getValue(String.class);
                   String date = dataSnapshot.child("Date").getValue(String.class);
                   updateIs_ReadedStatus(msg,SenderName, ID_reciver,date);
               }
               else  Log.d(TAG,"nothing updated!!!!!!!");


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.d(TAG, databaseError.getMessage());
           }
       });
   }

    @Override
    protected void onStop() {
        super.onStop();
        if (thread.isAlive()) { thread.interrupt();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread.isAlive()) { thread.interrupt();}
    }
}
