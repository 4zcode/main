package com.example.myapplication.message.chatRoom;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.doctors.advanceSearch.Doctors;
import com.example.myapplication.message.DBManagerMessage;
import com.example.myapplication.message.messageBoit.messageBoit;
import com.example.myapplication.utilities.tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class chatRoom extends AppCompatActivity {
    public final static String TAG = chatRoom.class.getSimpleName();


    private RecyclerView recyclerView;
    private  ArrayList<MessageChatItem> arrayMsg;
    private ChatRoomAdapter adapter;
    private FloatingActionButton button;
    private EditText editText;
    private DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Messages");;
    private FirebaseUser user1;
    private Map<String,Object> user,senderUser;
    private String ID_reciver,MessageRecever = "",SenderName, ReceiverImage;
    private SharedPreferences myPef ;
    private ProgressDialog mProgressDialog;
    private Handler handler;
    private Thread thread;
    private DBManagerMessage db;
    private LinearLayoutManager linearManager;
    private Parcelable recyclerState;

    //
    public static int PICK_IMAGE = 1;
    private StorageTask mUploadTask;
    private Uri mImageUri;
    private StorageReference mStorageRef= FirebaseStorage.getInstance().getReference("Messages");



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
        arrayMsg = new ArrayList<MessageChatItem>();
        ID_reciver = getIntent().getStringExtra(Doctors.RECIVER);
        ReceiverImage = getIntent().getStringExtra(Doctors.RECIVER_IMAGE);
        SenderName =getIntent().getStringExtra(Doctors.SENDER);
        this.setTitle(SenderName);
        handler= new Handler();
        user= new HashMap<String,Object>();
        senderUser = new HashMap<String,Object>();
        user1= FirebaseAuth.getInstance().getInstance().getCurrentUser();
        myPef = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        adapter= new ChatRoomAdapter(this,arrayMsg);
        recyclerView.setAdapter(adapter);
        linearManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearManager);
        recyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        db = new DBManagerMessage(this);
        db.open();
        String msg = db.getFullMessage(ID_reciver);
        DecodeFullMsg(msg,arrayMsg);
        adapter.notifyDataSetChanged();
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
        if (isNetworkAvailable(getBaseContext())) {
            updateAffichage(2000);
            Afficher(getBaseContext());
        }



        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom){
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        }
                    },100);
                }
            }
        });
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
                                recyclerState = recyclerView.getLayoutManager().onSaveInstanceState();

                            }
                        });
                    }
                }catch (InterruptedException e){
                    Log.d("akramTest", "error : "+e.getMessage() );

                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }


    public void Afficher(Context context){
        readData(context,new FireBaseCallBack() {
            @Override
            public void onCallBack(String msg) {

            }
        });
    }

    public void DecodeFullMsg(String msg,ArrayList<MessageChatItem> arrayMsg) {
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
                    msgImage = myPef.getString(KEY_USER_IMAGE, "R.drawable.profile");
                }
                arrayMsg.add(new MessageChatItem(detail[0], msgName, detail[1], detail[2], msgImage));
            }
        }
        Collections.sort(arrayMsg);
    }
    public void readData(final Context context , FireBaseCallBack fireBaseCallBack) {
        if (!ID_reciver.equals(user1.getUid())) {
            firebaseDatabase.child(user1.getUid()).child(ID_reciver).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean is_exist = dataSnapshot.child("message_envoyer").exists() && dataSnapshot.child("Date").exists()&&dataSnapshot.child("AllMsg").exists();
                    if (is_exist) {
                        String fullMsg = dataSnapshot.child("AllMsg").getValue(String.class);
                        String recentMsg = dataSnapshot.child("message_envoyer").getValue(String.class);
                        String date = dataSnapshot.child("Date").getValue(String.class);
                        MessageRecever = fullMsg;
                        boolean Is_Readed = dataSnapshot.child("Is_Readed").getValue(String.class).equals("true");
                        boolean falsy = dataSnapshot.child("Is_Readed").getValue(String.class).equals("false");
                        Log.d("akramTest","IS readed : "+Is_Readed);
                        Log.d("akramTest","falsy : "+falsy);

                        if ( !Is_Readed) {
                             Log.d("akramTest","msg not readed");
                            if(!db.CheckIsDataAlreadyInDBorNot(ID_reciver)) db.update(ID_reciver,SenderName,recentMsg,fullMsg,date, "true",ReceiverImage);
                            else db.update(ID_reciver,SenderName,recentMsg,fullMsg,date, "true",ReceiverImage);
                            updateIs_ReadedStatus(recentMsg, fullMsg, SenderName, ID_reciver, date);
                            DecodeFullMsg(fullMsg, arrayMsg);
                            adapter.notifyDataSetChanged();
                            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
                        }Log.d("akramTest","msg already readed");
                    }else Log.d("akramTest","msg doesn't exist in firebase");
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
        String message = editText.getText().toString().trim();


        if(isNetworkAvailable(getBaseContext()) && !message.isEmpty()) {
            mProgressDialog.show();

            String name_current_user = myPef.getString("userName", "Annonyme");
            DateFormat date = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
            String dt = date.format(Calendar.getInstance().getTime());
            String allMSG = MessageRecever  + user1.getUid() + "<br>" + message + "<br>"+dt+"@E1S9I!";
             updateUserReceiver(message,allMSG, SenderName, ID_reciver, dt);
             updateReceiverUser(name_current_user, message,allMSG, ID_reciver, dt);
             editText.getText().clear();
            tools.hideKeyBoard(chatRoom.this,view);
            mProgressDialog.dismiss();
            Afficher(getBaseContext());
        }else  {
        Toast.makeText(getBaseContext(),"pas internet",Toast.LENGTH_SHORT).show();
        }

    }
   public void updateUserReceiver(String message,String fullMsg,String senderName,String ID_reciver,String date){

        if (!ID_reciver.equals(user1.getUid())) {
            user.put("ID_Reciver", ID_reciver);
            user.put("Sender_Name", senderName);
            user.put("Sender_Image", ReceiverImage);
            user.put("message_envoyer", "Moi: " + message);
            user.put("Is_Readed", "false");
            user.put("Date", date);
            user.put("AllMsg", fullMsg);
            firebaseDatabase.child(user1.getUid()).child(ID_reciver).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "le message est envoyé", Toast.LENGTH_LONG).show();

                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();

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

            firebaseDatabase.child(ID_reciver).child(user1.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "le message est envoyé", Toast.LENGTH_SHORT).show();

                    } else {
                        String error;
                        error = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
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
            firebaseDatabase.child(user1.getUid()).child(ID_reciver).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatmenu, menu);
            return true;
    }



    @Override
    protected void onStart() {
        if (thread == null || !thread.isAlive()|| thread.isInterrupted()) {
            updateAffichage(2000);
        }
        super.onStart();

    }

    @Override
    protected void onResume() {
        if (thread == null || !thread.isAlive()|| thread.isInterrupted()) {
            updateAffichage(2000);
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        if (thread !=null && thread.isAlive()) {
            thread.interrupt();}
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (thread !=null && thread.isAlive()) { thread.interrupt();}
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        db.close();
        if (thread !=null && thread.isAlive()) { thread.interrupt();}
      super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_message) {
            AlertDialog message = new AlertDialog.Builder(this)
                    .setTitle("Avertissement")
                    .setMessage("Voullez vous vraiment supprimer ce message?")
                    .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firebaseDatabase.child(user1.getUid()).child(ID_reciver).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                       try {
                                           db.deleteF(ID_reciver);
                                       }catch (Exception e){
                                           e.printStackTrace();
                                           Log.d("deletetestunit","error : "+ e.getMessage());
                                       }
                                        Toast.makeText(getBaseContext(), "Le message a été supprimer", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getBaseContext(), messageBoit.class));

                                    }else {
                                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.pubelleicon)
                    .show();
        }else if (id == R.id.action_block){
            AlertDialog message = new AlertDialog.Builder(this)
                    .setTitle("Attention")
                    .setMessage("Voullez vous vraiment blouquer cette person?")
                    .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //    code
                            Toast.makeText(getBaseContext(), "Vous ne recevez plus des messages de cette person", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.sign1s)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }




    public void downlowd_image(View view) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();

            try {
                if (mImageUri != null) {
                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));
                    mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    EnvoyerImage(uri.toString());
                                }
                            });
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void EnvoyerImage(String imageUri) {
        Log.d("sendImageTest","inside EnvoyerImage ");

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }
        if (isNetworkAvailable(getBaseContext()) && !imageUri.isEmpty()) {
            mProgressDialog.show();
            String name_current_user = myPef.getString("userName", "Annonyme");
            DateFormat date = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
            String dt = date.format(Calendar.getInstance().getTime());
            String allMSG = MessageRecever + user1.getUid() + "<br>" + "!?*#*?!" + imageUri + "<br>" + dt + "@E1S9I!";
            updateUserReceiver("image", allMSG, SenderName, ID_reciver, dt);
            updateReceiverUser(name_current_user, "image", allMSG, ID_reciver, dt);
            mProgressDialog.dismiss();
            Toast.makeText(getBaseContext(),"l'image a été envoyer",Toast.LENGTH_LONG).show();
            Afficher(getBaseContext());
        } else {
            Toast.makeText(getBaseContext(), "pas internet", Toast.LENGTH_SHORT).show();
        }
    }
}