package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Medicament.medicament_activity;
import com.example.myapplication.Hospital.HopitalActivity;
import com.example.myapplication.Pharmacies.pharmacyActivity;
import com.example.myapplication.addProfile.AddDoctorProfile;
import com.example.myapplication.doctors.DoctorActivity;
import com.example.myapplication.location.MyLocation;
import com.example.myapplication.message.DBManagerMessage;
import com.example.myapplication.ui.login.Signin;
import com.example.myapplication.message.messageBoit;
import com.example.myapplication.utilities.PreferenceUtilities;
import com.example.myapplication.utilities.tools;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;





import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_IS_LOGIN;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_NUMBER_MESSAGES_NON_READ;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.getNbrMessageNoRead;
import static com.example.myapplication.utilities.PreferenceUtilities.updateNbrMessagesNoRead;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int LOCATION_PERMISSION = 99;

    private AppBarConfiguration mAppBarConfiguration;


    private TextView nav_user, nav_user_profil;
    private ImageView nav_user_image;
    private Thread thread;
    private SharedPreferences myPef;
    private FragmentRefreshListener fragmentRefreshListener;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Menu menu;
    private ProgressDialog progressDialog;
    private UpdateBbrMsgNonReadTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        locationManager = (LocationManager) getSystemService(getBaseContext().LOCATION_SERVICE);
        locationListener = new MyLocation(getBaseContext());
        tools.checkPermissions(MainActivity.this, locationManager, locationListener);

        View hView = navigationView.getHeaderView(0);
        nav_user = (TextView) hView.findViewById(R.id.nav_user_name);
        nav_user_profil = (TextView) hView.findViewById(R.id.nav_user_profile);
        nav_user_image = (ImageView) hView.findViewById(R.id.nav_user_image);

        myPef = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        PreferenceUtilities.saveUserInfo(this, FirebaseAuth.getInstance().getCurrentUser() != null);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
        progressDialog.setIndeterminate(true);

        new UpdateBbrMsgNonReadTask().execute();

    }


    public FragmentRefreshListener getFragmentRefreshListener() {

        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {

        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public void goToMedicament(View view) {

        startActivity(new Intent(this, medicament_activity.class));
    }

    public void goToDoctors(View view) {

        startActivity(new Intent(this, DoctorActivity.class));
    }

    public void goToPharmacies(View view) {

        startActivity(new Intent(this, pharmacyActivity.class));
    }

    public void goToHopitaux(View view) {
        startActivity(new Intent(this, HopitalActivity.class));

    }

    public void message(View view) {

        startActivity(new Intent(this, messageBoit.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        if (!myPef.getBoolean(KEY_IS_LOGIN, false) || FirebaseAuth.getInstance().getCurrentUser() == null) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_log_in) {
            Intent intent = new Intent(MainActivity.this, Signin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.action_log_out) {
            AlertDialog message = new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Are you sure you want sign out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.show();
                            FirebaseAuth.getInstance().signOut();
                            if (thread != null && thread.isAlive()) {
                                thread.interrupt();
                            }
                            SharedPreferences.Editor editor = myPef.edit();
                            editor.putBoolean(KEY_IS_LOGIN, false);
                            editor.apply();
                            PreferenceUtilities.saveUserInfo(getBaseContext(), false);
                            DefaultLayout();
                            menu.getItem(0).setVisible(true);
                            menu.getItem(1).setVisible(false);
                            DBManagerMessage db = new DBManagerMessage(getBaseContext());
                            db.open();
                            db.deleteAll();
                            db.close();
                            progressDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Sign out", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

/*
    public void refreshFregment() {
        synchronizeLayout();
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(10000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                synchronizeLayout();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }

 */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                    }
                    return;
                }
            }
        }
    }


    public void synchronizeLayout() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            nav_user.setText(myPef.getString(KEY_USER_NAME, DEFAULT_USER_NAME));
            nav_user_profil.setText("Mon Profile");
            nav_user_profil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), AddDoctorProfile.class));
                }
            });
            Glide.with(this)
                    .load(myPef.getString(KEY_USER_IMAGE, DEFAULT_USER_IMAGE))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(nav_user_image);
        } else DefaultLayout();
        if (getFragmentRefreshListener() != null) getFragmentRefreshListener().onRefresh();
    }

    public void DefaultLayout() {
        nav_user.setText("Sahti fi yedi");
        nav_user_profil.setText("Ajouter votre etablissement");
        nav_user_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AddDoctorProfile.class));
            }
        });
        nav_user_image.setImageResource(R.drawable.logo);

        if (getFragmentRefreshListener() != null) {
            getFragmentRefreshListener().onRefresh();
        }
    }

    @Override
    protected void onStart() {
        new UpdateBbrMsgNonReadTask().execute();
        super.onStart();

    }

    @Override
    protected void onResume() {
        new UpdateBbrMsgNonReadTask().execute();
        super.onResume();
    }


    @Override
    protected void onPause() {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        super.onPause();

    }

    @Override
    protected void onStop() {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        super.onStop();

    }

    @Override
    protected void onDestroy() {
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
        super.onDestroy();

    }

    public class UpdateBbrMsgNonReadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            updateNbrMessagesNoRead(getBaseContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            synchronizeLayout();
        }
    }



}

