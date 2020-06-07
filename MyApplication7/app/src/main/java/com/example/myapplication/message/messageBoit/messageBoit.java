package com.example.myapplication.message.messageBoit;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.message.DBManagerMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

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
    private LinearLayout mlinearLayout;
    private TextView emptyText;
    private ImageView emptyImage;
  //  private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
        mlinearLayout=(LinearLayout) findViewById(R.id.message_boit_empty_linear);
        emptyImage = (ImageView) findViewById(R.id.message_boit_empty_image);
        emptyText = (TextView) findViewById(R.id.message_boit_empty_text);
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
        Afficher(getBaseContext());


    }
    public void Afficher(Context context){
        linkedList = db.listMessages();
        if (linkedList.isEmpty()){
            mlinearLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mlinearLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        

        Collections.sort(linkedList);
        ada= new messageAdapter(this,linkedList);
        mRecyclerView.setAdapter(ada);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isNetworkAvailable(getBaseContext())){
            menu.getItem(0).setIcon(R.drawable.ic_sync_24dp);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_sync_problem_black_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh_message){
            if (isNetworkAvailable(getBaseContext())){
                Afficher(getBaseContext());
                invalidateOptionsMenu();
                Toast.makeText(getBaseContext(),"synchronise",Toast.LENGTH_SHORT).show();
            }else {
                    new AlertDialog.Builder(this)
                            .setTitle("Problem de connection")
                            .setMessage("Vouillez vous vérifie votre connection")
                            .setPositiveButton("J'ai compris", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        invalidateOptionsMenu();
                                    }
                            })
                            .setIcon(R.drawable.ic_perm_scan_wifi_black_24dp)
                            .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Afficher(getBaseContext());
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.open_enter,R.anim.nav_default_exit_anim);
    }
}
