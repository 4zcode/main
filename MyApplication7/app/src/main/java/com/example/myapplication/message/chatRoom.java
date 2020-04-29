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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.doctors.Doctors;
import com.example.myapplication.utilities.tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class chatRoom extends AppCompatActivity {
    public final static String TAG = chatRoom.class.getSimpleName();


    private RecyclerView recyclerView;
    private  ArrayList<MessageChatItem> arrayMsg;
    private ChatRoomAdapter adapter;
    private ScrollView scrollView;
    private FloatingActionButton button;
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

    private interface FireBaseCallBack {
        void onCallBack(String message);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        recyclerView =(RecyclerView) findViewById(R.id.message_chat_room_recycler);
        button =(FloatingActionButton) findViewById(R.id.envoyer_button);
        editText = (EditText) findViewById(R.id.edit_text_envoyer);
       // scrollView = (ScrollView) findViewById(R.id.scroll_chat);
        arrayMsg = new ArrayList<MessageChatItem>();
        ID_reciver = getIntent().getStringExtra(Doctors.RECIVER);
        ReceiverImage = getIntent().getStringExtra(Doctors.RECIVER_IMAGE);
        SenderName =getIntent().getStringExtra(Doctors.SENDER);
        this.setTitle(SenderName);
        handler= new Handler();
        user= new HashMap<String,Object>();
        senderUser = new HashMap<String,Object>();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Message");
        user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        myPef = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (isNetworkAvailable(getBaseContext())) {
            updateAffichage(1000);
            Afficher(getBaseContext());

        }else {
            Log.d("akramelkahba", "network not available" );
            Collections.sort(arrayMsg);
            adapter= new ChatRoomAdapter(this,arrayMsg);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        }
        //ScrollDown(3000);
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
                                Afficher(getBaseContext());
                            }
                        });
                    }
                }catch (InterruptedException e){e.printStackTrace();
                }
            }
        };

        thread.start();
    }


    public void Afficher(Context context){
        getMessageReceiver();
        readData(context,new FireBaseCallBack() {
            @Override
            public void onCallBack(String msg) {

            }
        });
    }
    public void readData(final Context context , FireBaseCallBack fireBaseCallBack) {
        if (!ID_reciver.equals(user1.getUid())) {
            DoctorsRef.child("ENVOYE").child(user1.getUid()).child(ID_reciver).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ( dataSnapshot.child("AllMsg").exists()) {
                        String msg = dataSnapshot.child("AllMsg").getValue(String.class);
                        MessageRecever=msg;
                        String[] fullMsg = msg.split("@E1S9I!");
                        arrayMsg.clear();
                        for (String petitMsg : fullMsg) {
                            String[] detail = petitMsg.split("<br>");
                            if (detail.length == 3) {
                                String msgName = "Anonyme";
                                String msgImage = "R.drawble.doctorm";
                                if (detail[0].equals(ID_reciver)) {
                                    msgName = SenderName;
                                    msgImage = ReceiverImage;
                                } else if (detail[0].equals(user1.getUid())) {
                                    msgName = myPef.getString(KEY_USER_NAME, "Annonyme");
                                    msgImage = myPef.getString(KEY_USER_IMAGE, "R.drawable.doctorm");
                                }
                                arrayMsg.add(new MessageChatItem(detail[0], msgName, detail[1], detail[2], msgImage));
                            } else Log.d("akramelkahba", "detail.length : " + detail.length);

                        }
                        Collections.sort(arrayMsg);
                        adapter= new ChatRoomAdapter(context,arrayMsg);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
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
            DateFormat date = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
            String dt = date.format(Calendar.getInstance().getTime());
            String allMSG = MessageRecever  + user1.getUid() + "<br>" + message + "<br>"+dt+"@E1S9I!";
            updateUserReceiver(message,allMSG, SenderName, ID_reciver, dt);
            updateReceiverUser(name_current_user, message,allMSG, ID_reciver, dt);
            mProgressDialog.dismiss();
            editText.getText().clear();
            tools.hideKeyBoard(chatRoom.this,view);
            Afficher(getBaseContext());
        }else             mProgressDialog.dismiss();

    }
   public void updateUserReceiver(String message,String fullMsg,String senderName,String ID_reciver,String date){

        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", ID_reciver);
            user.put("Sender_Name", senderName);
            user.put("Sender_Image", ReceiverImage);
            user.put("message_envoyer", "Moi: " + message);
            user.put("Is_Readed", "true");
            user.put("Date", date);
            user.put("AllMsg", fullMsg);

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
   public void updateReceiverUser(String name_current_user,String message,String fullMsg,String ID_reciver,String date){
        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", user1.getUid());
            user.put("Sender_Name", name_current_user);
            user.put("Sender_Image", myPef.getString(KEY_USER_IMAGE,DEFAULT_USER_IMAGE));
            user.put("message_envoyer", message);
            user.put("Is_Readed", "false");
            user.put("Date", date);
            user.put("AllMsg", fullMsg);

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


   public void ScrollDown(int duree){
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               scrollView.fullScroll(View.FOCUS_DOWN);
           }
       },duree);
   }
   public void updateIs_ReadedStatus(String message,String fullMsg,String senderName,String ID_reciver,String date){
        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", ID_reciver);
            user.put("Sender_Name", senderName);
            user.put("Sender_Image", ReceiverImage);
            user.put("message_envoyer", message);
            user.put("Is_Readed", "true");
            user.put("AllMsg", fullMsg);
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
               boolean is_exist = dataSnapshot.child("message_envoyer").exists() && dataSnapshot.child("Date").exists()&&dataSnapshot.child("AllMsg").exists();
               if (is_exist) {
                   String msg = dataSnapshot.child("message_envoyer").getValue(String.class);
                   String date = dataSnapshot.child("Date").getValue(String.class);
                   String fullMSG = dataSnapshot.child("AllMsg").getValue(String.class);
                   updateIs_ReadedStatus(msg,fullMSG,SenderName, ID_reciver,date);
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
        if (thread !=null && thread.isAlive()) { thread.interrupt();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread !=null && thread.isAlive()) { thread.interrupt();}
    }
}
