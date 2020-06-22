package com.example.myapplication.message.messageBoit;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.message.DBManagerMessage;


import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class messageBoit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public final static String TAG = messageBoit.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private messageAdapter mAdapter;
    private DBManagerMessage db;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout mlinearLayout;
    private TextView emptyText;
    private ImageView emptyImage;
  //  private Timer timer;
  //
  private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_boit);
        //
        dbHelper = new DatabaseHelper(this);

        mlinearLayout=(LinearLayout) findViewById(R.id.message_boit_empty_linear);
        emptyImage = (ImageView) findViewById(R.id.message_boit_empty_image);
        emptyText = (TextView) findViewById(R.id.message_boit_empty_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessage);


        mAdapter = new messageAdapter(this,null);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        db = new DBManagerMessage(getBaseContext());
        db.open();


        mRecyclerView.setAdapter(mAdapter);


    }
    public void Afficher(Boolean vide){
        if (vide){
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        if(id == 0) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    final String[] hopitalColumns = {
                            DatabaseHelper._ID,
                            DatabaseHelper._ID_MESSAGE_SENDER_FIREBASE,
                            DatabaseHelper.SENDER_MESSAGE_NAME,
                            DatabaseHelper.RECENT_MESSAGE,
                            DatabaseHelper.MESSAGE_RECENT_DATE,
                            DatabaseHelper.IS_READ,
                            DatabaseHelper.IMAGE_SENDER_MESSAGE_URL};

                    String OrderBy = "datetime("+DatabaseHelper.MESSAGE_RECENT_DATE+") DESC" ;
                    Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_MESSAGES, hopitalColumns,
                            null, null, null, null, OrderBy,"20" );
                Afficher(cursor == null);
                return cursor;
                }

            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == 0)  {
            mAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == 0)  {
            mAdapter.changeCursor(null);
        }
    }
}
