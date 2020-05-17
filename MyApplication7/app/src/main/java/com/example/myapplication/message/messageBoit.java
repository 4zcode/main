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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
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

   /*     final Handler handler = new Handler();
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

    */



            Afficher(getBaseContext());



    }
    public void Afficher(Context context){
        Log.d("AFicherAkram","inside afficher");
        linkedList = db.listMessages();
        Collections.sort(linkedList);
        ada= new messageAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        Log.d("AFicherAkram","finish afficher");

    }


    @Override
    protected void onStart() {
        super.onStart();
        Afficher(getBaseContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.myapplication.message");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
