package com.example.myapplication.Pharmacies;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.ArrayList;

import static com.example.myapplication.utilities.tools.getCommuns;
import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class pharmacyActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private final DatabaseReference PhREf = FirebaseDatabase.getInstance().getReference().child("pharmacies");
    private RecyclerView mRecyclerView;
    private DBManagerPharmacy dbManager;
    private pharmacyAdapter mAdapter;
    private Spinner spinnerWilaya, spinnerCommuns;
    private ArrayAdapter<CharSequence>  commmunsCodeAdapter;
    private String mWilayaName ="Blida", commune;
    private String mSpeciality, mSearch="";
    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies);

        //
        dbHelper = new DatabaseHelper(this);

        //
        mRecyclerView = findViewById(R.id.pharmacies_recycler_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerWilaya = findViewById(R.id.spinner_pharmacie_wilaya);
        spinnerCommuns = findViewById(R.id.spinner_pharmacie_communs);


        dbManager = new DBManagerPharmacy(this);
        dbManager.open();



        mAdapter = new pharmacyAdapter(this,null);
        mRecyclerView.setAdapter(mAdapter);


        new UpdatepharmacyListTask().execute();


        spinnerWilaya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCommuns.setVisibility(View.VISIBLE);
                mWilayaName = String.valueOf(spinnerWilaya.getSelectedItem());
                commmunsCodeAdapter = new ArrayAdapter<CharSequence>(getBaseContext(), android.R.layout.simple_spinner_item, getCommuns(position));
                commmunsCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCommuns.setAdapter(commmunsCodeAdapter);
                getLoaderManager().restartLoader(0, null, pharmacyActivity.this);
                //


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void readData() {

        PhREf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    boolean Is_Exist =ds.child("_ID_Firebase").exists()
                            && ds.child("ImageUrl").exists()
                            && ds.child("Name").exists()
                            && ds.child("Place").exists()
                            && ds.child("Wilaya").exists()
                            && ds.child("Commune").exists()
                            && ds.child("Phone").exists()
                            && ds.child("Time").exists()
                            && ds.child("Description").exists()
                            && ds.child("Time").exists();
                    if (Is_Exist) {
                        final String id_firebase = ds.child("_ID_Firebase").getValue(String.class);
                        final String Name = ds.child("Name").getValue(String.class);
                        final String Place = ds.child("Place").getValue(String.class);
                        final String Phone = ds.child("Phone").getValue(String.class);
                        final String time = ds.child("Time").getValue(String.class);
                        final String wilaya= ds.child("Wilaya").getValue(String.class);
                        final String commune= ds.child("Commune").getValue(String.class);
                        final String description = ds.child("Description").getValue(String.class);
                        final String ImageUrl = ds.child("ImageUrl").getValue(String.class);
                        boolean exist = ds.child("PharmacieExist").getValue(boolean.class);

                        Log.d("pharmacy_test","pharmacy exist");


                        if (!exist){
                            new AsyncTask<Void,Void,Void>(){

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    dbManager.deleteByFireBaseId(id_firebase);
                                    return null;
                                }
                            };

                        } else if (dbManager.CheckIsDataAlreadyInDBorNot(id_firebase)) {

                            new AsyncTask<Void,Void,Void>(){

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    dbManager.update(id_firebase, Name, Place, Integer.parseInt(wilaya),Integer.parseInt(commune),Phone, time,ImageUrl, description);
                                    return null;
                                }
                            };

                        } else {
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    dbManager.insert(id_firebase, Name, Place, Integer.parseInt(wilaya), Integer.parseInt(commune), Phone, time, ImageUrl, description);
                                    return null;
                                }
                            };

                        }
                    } else Log.d("pharmacy_test","is not exist");
                }
               dbManager.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("pharmacyTest","error : "+databaseError.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView =null;
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null){
            searchView =(SearchView) searchItem.getActionView();
        } else  Log.d("SearchTestAkram","searchItem  null ");

        if (searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint("Chercher");
            searchView.setIconified(false);
            final SearchView finalSearchView = searchView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearch=query;
                    getLoaderManager().restartLoader(0, null, pharmacyActivity.this);
                    finalSearchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mSearch=newText;
                    getLoaderManager().restartLoader(0, null, pharmacyActivity.this);
                    return true;
                }
            });
            searchView.clearFocus();
        }else  Log.d("SearchTestAkram","searchView  null ");

        return true;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        if(id == 0) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    final String[] pharmaciesColumns = {
                            DatabaseHelper._ID,
                            DatabaseHelper._ID_PHARMA_FIREBASE,
                            DatabaseHelper.NAME_PHARMA,
                            DatabaseHelper.PLACE_PHARMA,
                            DatabaseHelper.WILAYA,
                            DatabaseHelper.COMMUNE,
                            DatabaseHelper.PHONE_PHARMA,
                            DatabaseHelper.IMAGE_HOSPITAL_URL};

                    String selection=null;
                    String[] selectionArgs= null;
                    if (mWilayaName.equals("Wilaya")){
                        selection = DatabaseHelper.NAME_PHARMA + " LIKE ?";
                        selectionArgs = new String[]{"%"+mSearch+"%"};
                    }else {
                        selection = DatabaseHelper.NAME_PHARMA + " LIKE ? AND "+DatabaseHelper.PLACE_PHARMA + " LIKE ?";
                        selectionArgs = new String[]{"%"+mSearch+"%", "%"+mWilayaName.toUpperCase()};
                    }


                    String OrderBy = DatabaseHelper.NAME_PHARMA ;
                    return db.query(DatabaseHelper.TABLE_NAME_PHARMACIE, pharmaciesColumns,selection, selectionArgs, null, null, OrderBy ,"20");

                 //   return db.query(DatabaseHelper.TABLE_NAME_PHARMACIE, pharmaciesColumns,null, null, null, null, null);
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

    public class UpdatepharmacyListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (isNetworkAvailable(getBaseContext())) {
                readData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getLoaderManager().restartLoader(0, null, pharmacyActivity.this);
            Toast.makeText(getBaseContext(),"Updated",Toast.LENGTH_LONG).show();
        }
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

}
