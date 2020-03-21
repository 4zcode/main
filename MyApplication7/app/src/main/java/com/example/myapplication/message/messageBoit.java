package com.example.myapplication.message;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.chatRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class messageBoit extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Message> linkedList;
    private DatabaseReference firebaseDatabase;
    private FirebaseUser user1;
    private final DatabaseReference DoctorsRef = FirebaseDatabase.getInstance().getReference().child("Message");
    private Thread thread;
    private messageAdapter ada;

    private interface FireBaseCallBack {
        void onCallBack(String message);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Message");
        user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage);
        linkedList = new ArrayList<Message>();
        ada= new messageAdapter(this,linkedList);
        Afficher();
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
        mRecyclerView.setAdapter(ada);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));


    }
    public void Afficher(){
        linkedList.clear();
        readData(new messageBoit.FireBaseCallBack() {
            @Override
            public void onCallBack(String msg) {
            ada.notifyDataSetChanged();
            }
        });
    }
    public void readData(messageBoit.FireBaseCallBack fireBaseCallBack) {
        DoctorsRef.child("ENVOYE").child(user1.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){


                    String SenderName = ds.child("Sender_Name").getValue(String.class);

                    String message = ds.child("message_envoyer").getValue(String.class);
                    String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                    Log.d("messageBoitTest","sender name est : "+ID_firebase);
                    linkedList.add(new Message(ID_firebase,SenderName,message,R.drawable.doctorm));
                }
                mRecyclerView.setAdapter(ada);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("messageBoitTest", databaseError.getMessage());
            }
        });
    }
}
