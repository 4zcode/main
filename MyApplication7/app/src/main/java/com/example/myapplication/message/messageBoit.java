package com.example.myapplication.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.services.UpdateMsgDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class messageBoit extends AppCompatActivity {
    public final static String TAG = messageBoit.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private ArrayList<MessageItem> linkedList;
    private DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Messages");
    private FirebaseUser user;
    private Thread thread;
    private  java.util.Date date;
    private messageAdapter ada;
    private DBManagerMessage db;
    private LinearLayoutManager linearLayoutManager;
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean refresh = intent.getBooleanExtra("refresh",true);
            Log.d("ServiceAkramTestt","refresh: "+refresh);
            if (linkedList != null && db != null && ada != null&& refresh ){
                Log.d("ServiceAkramTestt","refreshed yes ");

                linkedList = db.listMessages();
                Collections.sort(linkedList);
                ada.notifyDataSetChanged();
            }
        }
    };


    private interface FireBaseCallBack {
        void onCallBack(String message);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
        user= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage);
        linkedList = new ArrayList<MessageItem>();
        ada= new messageAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        db = new DBManagerMessage(getBaseContext());
        db.open();
        linkedList = db.listMessages();
        Collections.sort(linkedList);
        ada= new messageAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ServiceAkramTestt","on creat new service");

                        startService(new Intent(messageBoit.this, UpdateMsgDB.class));
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,5000);



        /*if (isNetworkAvailable(getBaseContext())) {
            Afficher(getBaseContext());
        }

         */


    }
    public void Afficher(Context context){
        linkedList.clear();
        readData(context,new messageBoit.FireBaseCallBack() {
            @Override
            public void onCallBack(String msg) {

            }
        });
    }
    public void readData(final Context context, messageBoit.FireBaseCallBack fireBaseCallBack) {
        firebaseDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 db.deleteAll();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    boolean is_exist = ds.child("message_envoyer").exists() && ds.child("Sender_Name").exists()&& ds.child("ID_Reciver").exists()&& ds.child("Is_Readed").exists()&& ds.child("Date").exists()&& ds.child("AllMsg").exists();
                    if (is_exist) {
                  String SenderName = ds.child("Sender_Name").getValue(String.class);
                        String SenderImage;
                  if (ds.child("Sender_Image").exists()) {
                       SenderImage = ds.child("Sender_Image").getValue(String.class);
                  }else SenderImage = "R.drawable.doctorm";
                  String message = ds.child("message_envoyer").getValue(String.class);
                  String fullMsg = ds.child("AllMsg").getValue(String.class);
                  String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                  String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                  String Date = ds.child("Date").getValue(String.class);
                  SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
           try {
                date = format.parse(Date);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           if (!ID_firebase.equals(user.getUid())) {
               if(db.CheckIsDataAlreadyInDBorNot(ID_firebase)){
                   db.update(ID_firebase,SenderName,message,fullMsg,Date,Is_Readed,SenderImage);
               }else{
                   db.insert(ID_firebase,SenderName,message,fullMsg,Date,Is_Readed,SenderImage);

               }
           }
         }
                }
                linkedList = db.listMessages();
                Collections.sort(linkedList);
                ada= new messageAdapter(context,linkedList);
                mRecyclerView.setAdapter(ada);
                linearLayoutManager = new LinearLayoutManager(context);
                mRecyclerView.setLayoutManager(linearLayoutManager);            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.myapplication.message");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }


    @Override
    protected void onPause() {
       // if (thread !=null && thread.isAlive()) { thread.interrupt();}

        super.onPause();
    }

    @Override
    protected void onStop() {
      //  if (thread !=null && thread.isAlive()) { thread.interrupt();}

        Log.d("messageofflinetest","onStop");

        super.onStop();
       // db.close();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

    }

    @Override
    protected void onDestroy() {
    //    if (thread !=null && thread.isAlive()) { thread.interrupt();}

        Log.d("messageofflinetest","onDestroy");

        super.onDestroy();
      //  db.close();

    }
}
