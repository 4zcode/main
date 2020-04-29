package com.example.myapplication.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
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
import java.util.Date;

import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class messageBoit extends AppCompatActivity {
    public final static String TAG = messageBoit.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private ArrayList<MessageItem> linkedList;
    private DatabaseReference firebaseDatabase;
    private FirebaseUser user;
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Message");
    private Thread thread;
    java.util.Date date;
    private messageAdapter ada;
    private DBManagerMessage db;



    private interface FireBaseCallBack {
        void onCallBack(String message);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Message");
        user= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage);
        linkedList = new ArrayList<MessageItem>();
        db = new DBManagerMessage(getBaseContext());
        db.open();
        if (isNetworkAvailable(this)) {
            Afficher(this);
        }else {
            linkedList = db.listMessages();
            Collections.sort(linkedList);
            ada= new messageAdapter(this,linkedList);
            mRecyclerView.setAdapter(ada);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        /*
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(5000);
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
        DoctorsRef.child("ENVOYE").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    boolean is_exist = ds.child("message_envoyer").exists() && ds.child("Sender_Name").exists()&& ds.child("ID_Reciver").exists()&& ds.child("Is_Readed").exists()&& ds.child("Date").exists();
                    if (is_exist) {
                  String SenderName = ds.child("Sender_Name").getValue(String.class);
                        String SenderImage;
                  if (ds.child("Sender_Image").exists()) {
                       SenderImage = ds.child("Sender_Image").getValue(String.class);
                  }else SenderImage = "R.drawable.doctorm";
                  String message = ds.child("message_envoyer").getValue(String.class);
                  String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                  String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                  String Date = ds.child("Date").getValue(String.class);
                  SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy, HH:mm:SS");
           try {
                date = format.parse(Date);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           if (!ID_firebase.equals(user.getUid())) {
               if(db.CheckIsDataAlreadyInDBorNot(ID_firebase)){
                   db.update(ID_firebase,SenderName,message,"",Date,Is_Readed,SenderImage);
               }else{
                   db.insert(ID_firebase,SenderName,message,"",Date,Is_Readed,SenderImage);

               }
           }
         }
                }
                linkedList = db.listMessages();
                Collections.sort(linkedList);
                ada= new messageAdapter(context,linkedList);
                mRecyclerView.setAdapter(ada);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        Log.d("messageofflinetest","onStop");

        super.onStop();
       // db.close();
    }

    @Override
    protected void onDestroy() {
        Log.d("messageofflinetest","onDestroy");

        super.onDestroy();
      //  db.close();

    }
}
