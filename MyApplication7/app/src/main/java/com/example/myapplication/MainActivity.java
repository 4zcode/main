package com.example.myapplication;

import android.Manifest;
import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Laboratoir.LaboActivity;
import com.example.myapplication.Hospital.HopitalActivity;
import com.example.myapplication.Pharmacies.pharmacyActivity;
import com.example.myapplication.Profiles.UserProfile;
import com.example.myapplication.doctors.DoctorActivity;
import com.example.myapplication.location.MyLocation;
import com.example.myapplication.message.DBManagerMessage;
import com.example.myapplication.services.NbrMSGnonRead;
import com.example.myapplication.ui.login.Signin;
import com.example.myapplication.message.messageBoit.messageBoit;
import com.example.myapplication.ui.login.SignupActivity;
import com.example.myapplication.utilities.PreferenceUtilities;
import com.example.myapplication.utilities.tools;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;



import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.DEFAULT_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_IS_LOGIN;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.firstTime;
import static com.example.myapplication.utilities.tools.readAllData;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int LOCATION_PERMISSION = 99;

    private AppBarConfiguration mAppBarConfiguration;


    private TextView nav_user, nav_user_profil;
    private ImageView nav_user_image;
    private SharedPreferences myPef;
    private FragmentRefreshListener fragmentRefreshListener;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Menu menu;
    private ProgressDialog progressDialog;
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String NbrMsgNoRead = intent.getStringExtra("NbrMsgs");
            Log.d("ServiceAkramTest","Nbr msg: "+NbrMsgNoRead);
            synchronizeLayout();
        }
    };

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

         final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       Log.d("ServiceAkramTest","on creat new service");

                       startService(new Intent(MainActivity.this, NbrMSGnonRead.class));
                   }
               });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0,2000);

        if (firstTime(this)) {
            Log.d("AkramTestdatabase","first Time");
            new UpdateDataBaseTask().execute();
        } else  Log.d("AkramTestdatabase","no first time");

    }

    public FragmentRefreshListener getFragmentRefreshListener() {

        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {

        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public void goToLabos(View view) {

        startActivity(new Intent(this, LaboActivity.class));
    }

    public void goToDoctors(View view) {

        startActivity(new Intent(this, DoctorActivity.class));
        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
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
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (FirebaseAuth.getInstance().getCurrentUser()!= null){
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }else {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_log_in) {
            startActivity(new Intent(MainActivity.this, Signin.class));
        } else if (id == R.id.action_log_out) {
            AlertDialog message = new AlertDialog.Builder(this)
                    .setTitle("Avertissement")
                    .setMessage("Voullez-vous vraiment Sign out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.show();
                            FirebaseAuth.getInstance().signOut();
                            SharedPreferences.Editor editor = myPef.edit();
                            editor.putBoolean(KEY_IS_LOGIN, false);
                            editor.apply();
                            PreferenceUtilities.saveUserInfo(getBaseContext(), false);
                            DefaultLayout();
                            invalidateOptionsMenu();
                            DBManagerMessage db = new DBManagerMessage(getBaseContext());
                            db.open();
                            db.deleteAll();
                            db.close();
                            progressDialog.dismiss();
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
                    startActivity(new Intent(getBaseContext(), UserProfile.class));

                }
                    /*
                   switch (myPef.getString(KEY_USER_TYPE,DEFAULT_USER_TYPE)){
                        case "Doctor":
                            startActivity(new Intent(getBaseContext(), AddDoctorProfile.class));
                            break;
                        case "Pharmacy":
                            startActivity(new Intent(getBaseContext(), AddPharmacyProfil.class));
                            break;
                        case "Hopital":
                            startActivity(new Intent(getBaseContext(), AddHospitalProfil.class));
                            break;
                        case "Labo":
                            startActivity(new Intent(getBaseContext(), AddLaboProfile.class));
                            break;
                        case "Donnateur":
                            Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();;
                            break;
                        case "Normal":
                            Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();;
                            break;
                    }

                }
                */
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
        nav_user_profil.setText("Ajouter votre établissement");
        nav_user_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignupActivity.class));
            }
        });
        nav_user_image.setImageResource(R.drawable.logo);

        if (getFragmentRefreshListener() != null) {
            getFragmentRefreshListener().onRefresh();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.myapplication");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public class UpdateDataBaseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            readAllData(getBaseContext());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }





}

