package com.example.myapplication.Hospital;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.CursorLoader;

import java.util.ArrayList;

import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class HopitalActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private DBManagerHospital dbManagerHospital;
    private RecyclerView mRecyclerView;
    private HopitalsAdapter mAdapter;
    private String mWillayaName= "Wilaya", mSearch ="";
    private Integer mHopitalType = 0;
    private final DatabaseReference hopitalRefrence = FirebaseDatabase.getInstance().getReference().child("Hopitals");
    private Spinner mWillayaSpinner;
    private RadioGroup mRadioGroup;

    //
    private DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);


        //
        dbHelper = new DatabaseHelper(this);


        //
        dbManagerHospital = new DBManagerHospital(HopitalActivity.this);


        //
        mRadioGroup = findViewById(R.id.hopital_activity_radiobottongroup);

        mWillayaSpinner = findViewById(R.id.spinner_wilaya);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.public_hopital){
                    mHopitalType = 0;
                    loadData();
                }else if(checkedId == R.id.private_hopital){
                    mHopitalType = 1;
                    loadData();
                }
            }
        });

       if (isNetworkAvailable(this)) {
            readData();
        }



        mAdapter = new HopitalsAdapter(HopitalActivity.this,null);
        mRecyclerView.setAdapter(mAdapter);

        mWillayaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWillayaName = String.valueOf(mWillayaSpinner.getSelectedItem());
               loadData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
             // rien se passe
            }
        });
    }


   public void readData() {
       hopitalRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               dbManagerHospital.open();
               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   boolean Is_Exist =ds.child("hospital_ID_Firebase").exists() &&ds.child("imageResource").exists() && ds.child("hopitalName").exists() && ds.child("hopitalPlace").exists() && ds.child("hopitalContact").exists() ;
                   if (Is_Exist) {
                       String id_firebase = ds.child("hospital_ID_Firebase").getValue(String.class);
                       String Name = ds.child("hopitalName").getValue(String.class);
                       String Place = ds.child("hopitalPlace").getValue(String.class);
                       String Phone = ds.child("hopitalContact").getValue(String.class);
                       String ImageUrl = ds.child("imageResource").getValue(String.class);
                       if (dbManagerHospital.CheckIsDataAlreadyInDBorNot(id_firebase)) {
                           dbManagerHospital.update(id_firebase, Name, "Il n'y a aucun description",Place,0,Phone,"Il n'y a aucun service",ImageUrl);
                       } else {
                           dbManagerHospital.insert(id_firebase,Name,"Il n'y a aucun description",Place,0,Phone,"Il n'y a aucun service",ImageUrl);

                       }
                   }
               }
               loadData();


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
           }
       });


   }

    @Override
    protected void onResume() {
        super.onResume();
       getLoaderManager().restartLoader(0, null, this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
           SearchView searchView =null;
            getMenuInflater().inflate(R.menu.doctorsearch,menu);
            MenuItem searchItem = menu.findItem(R.id.search_doctors);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            if (searchItem != null){
                searchView =(SearchView) searchItem.getActionView();
            } else  Log.d("SearchTestAkram","searchItem  null ");

            if (searchView != null){
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setQueryHint("Chercher");
                searchView.setIconified(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mSearch=query;
                        loadData();

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mSearch=newText;

                       loadData();
                        return true;
                    }
                });
                    searchView.clearFocus();
            }else  Log.d("SearchTestAkram","searchView  null ");

        return true;
    }
    private void loadData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String[] hopitalColumns = {
                DatabaseHelper._ID,
                DatabaseHelper._ID_HOSPITAL_FIREBASE,
                DatabaseHelper.NAME__HOSPITAL,
                DatabaseHelper.PLACE__HOSPITAL,
                DatabaseHelper.NUMBER__HOSPITAL,
                DatabaseHelper.IMAGE_HOSPITAL_URL};

         String selection=null;
         String[] selectionArgs= null;
        if (mWillayaName.equals("Wilaya")){
            selection = DatabaseHelper.TYPE__HOSPITAL + " = ? AND " + DatabaseHelper.NAME__HOSPITAL + " LIKE ?";
            selectionArgs = new String[]{String.valueOf(mHopitalType),"%"+mSearch+"%"};
        }else {
            selection = DatabaseHelper.TYPE__HOSPITAL + " = ? AND " +DatabaseHelper.NAME__HOSPITAL + " LIKE ? AND "+DatabaseHelper.PLACE__HOSPITAL + " LIKE ?";
            selectionArgs = new String[]{String.valueOf(mHopitalType),"%"+mSearch+"%", "%"+mWillayaName.toUpperCase()};
        }


        String OrderBy = DatabaseHelper.NAME__HOSPITAL ;
        final Cursor hopitalCursor = db.query(DatabaseHelper.TABLE_NAME_HOSPITAL, hopitalColumns,
                selection, selectionArgs, null, null, OrderBy);
        mAdapter.changeCursor(hopitalCursor);
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
                            DatabaseHelper._ID_HOSPITAL_FIREBASE,
                            DatabaseHelper.NAME__HOSPITAL,
                            DatabaseHelper.PLACE__HOSPITAL,
                            DatabaseHelper.NUMBER__HOSPITAL,
                            DatabaseHelper.IMAGE_HOSPITAL_URL};

                    String selection=null;
                    String[] selectionArgs= null;
                    if (mWillayaName.equals("Wilaya")){
                        selection = DatabaseHelper.TYPE__HOSPITAL + " = ? AND " + DatabaseHelper.NAME__HOSPITAL + " LIKE ?";
                        selectionArgs = new String[]{String.valueOf(mHopitalType),"%"+mSearch+"%"};
                    }else {
                        selection = DatabaseHelper.TYPE__HOSPITAL + " = ? AND " +DatabaseHelper.NAME__HOSPITAL + " LIKE ? AND "+DatabaseHelper.PLACE__HOSPITAL + " LIKE ?";
                        selectionArgs = new String[]{String.valueOf(mHopitalType),"%"+mSearch+"%", "%"+mWillayaName.toUpperCase()};
                    }


                    String OrderBy = DatabaseHelper.NAME__HOSPITAL ;
                    return db.query(DatabaseHelper.TABLE_NAME_HOSPITAL, hopitalColumns,
                            selection, selectionArgs, null, null, OrderBy);
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