package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.Drugs.medicament_activity;
import com.example.myapplication.Hospitals.HopitalActivity;
import com.example.myapplication.Pharmacies.pharmacies;
import com.example.myapplication.doctors.DoctorActivity;
import com.example.myapplication.location.MyLocation;
import com.example.myapplication.ui.login.Signin;
import com.example.myapplication.ui.login.SignupActivity;
import com.example.myapplication.message.messageBoit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference DoctorsRef = rootRef.child("Doctor");
    private final DatabaseReference MessageNonReadRef = rootRef.child("Message").child("ENVOYE");
    private TextView nav_user;
    private Thread thread;
    private SharedPreferences myPef;
    public static final int LOCATION_PERMISSION = 99;
    private Integer count = new Integer(0);
    private FragmentRefreshListener fragmentRefreshListener;
    private LocationManager locationManager ;
    private LocationListener locationListener ;





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
       locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       locationListener = new MyLocation(getBaseContext());
       checkPermissions();
       View hView = navigationView.getHeaderView(0);
       nav_user = (TextView) hView.findViewById(R.id.nav_name);
       myPef =getSharedPreferences("userPref", Context.MODE_PRIVATE);
       saveUserInfo(myPef);
       nav_user.setText(myPef.getString("userName","Sahti fi yedi"));
        if (isNetworkAvailable()){
            refreshFregment();}
    }
    public void saveUserInfo(final SharedPreferences myPef){
        if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
            DoctorsRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Name = dataSnapshot.child("NameDoctor").getValue(String.class);
                    SharedPreferences.Editor editor = myPef.edit();
                    editor.putString("userName",Name);
                    editor.apply();
                    nav_user.setText(myPef.getString("userName","Sahti fi yedi"));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            updateNbrMessagesNoRead();

        }

    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }
    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener){
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public void med(View view) {
        Intent intent3 = new Intent(this, medicament_activity.class);
        startActivity(intent3);

    }

    public void doc(View view) {
        Intent intent2 = new Intent(this, DoctorActivity.class);
        startActivity(intent2);

    }

    public void pharma(View view) {
        Intent intent = new Intent(this, pharmacies.class);
        startActivity(intent);

    }

    public void hos(View view) {
        Intent intent4 = new Intent(this, HopitalActivity.class);
        startActivity(intent4);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void insertion(View view){
        Intent intent=new Intent(this, Insertion.class);
        startActivity(intent);
    }
    public void profile(View view){
        Intent intent=new Intent(this, profile.class);
        startActivity(intent);
    }
    public void log(View view){
        Intent intent=new Intent(this, SignupActivity.class);
        startActivity(intent);

    }
    public void insecre(View view){
        Intent intent=new Intent(this, Signin.class);
        startActivity(intent);
    }
     public void message(View view){

        startActivity(new Intent(this, messageBoit.class));
      }
     public void getNbrMessageNoRead(){
         MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).child("Message_Non_Read").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 SharedPreferences.Editor editor = myPef.edit();
                 if (dataSnapshot.child("nbr_Messages").exists()){
                     String  NbrMsg = dataSnapshot.child("nbr_Messages").getValue(String.class);
                     editor.putString("NbrMessageNoRead",NbrMsg);
                     editor.apply();
                 }else{
                     editor.putString("NbrMessageNoRead","0");
                     editor.apply();
                     Log.d("here_the_problem","n'existe pas: ");

                 }

             }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
     }

    public void updateNbrMessagesNoRead(){
        MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = 0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if (ds.child("Is_Readed").exists()){
                        String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                        if (Is_Readed.equals("false")){
                            count += 1;
                        }
                    }
                }
                Map<String,Object> user= new HashMap<String,Object>();
                user.put("nbr_Messages",count.toString());
                MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).child("Message_Non_Read").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            getNbrMessageNoRead();

                        }else{
                            String error;
                            error=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              Log.d("here_the_problem",databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }

    public void refreshFregment(){
        if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!thread.isInterrupted()) {
                            Thread.sleep(10000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateNbrMessagesNoRead();
                                    if (getFragmentRefreshListener() != null) {
                                        getFragmentRefreshListener().onRefresh();
                                    }
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

    }
    private void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermission = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermission.isEmpty()){
                ActivityCompat.requestPermissions(this,listPermission.toArray(new String[listPermission.size()]),LOCATION_PERMISSION);
            }
        }else {
          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch(requestCode){
            case LOCATION_PERMISSION: {
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                    }
                    return;
                }
            }
        }
    }
    public boolean isNetworkAvailable() {
        boolean HaveConnectWIFI = false;
        boolean HaveConnectMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : activeNetworkInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    HaveConnectWIFI = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    HaveConnectMobile = true;
        }
        return HaveConnectMobile || HaveConnectWIFI;
    }
    @Override
    protected void onStart() {
        super.onStart();
        refreshFregment();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (thread.isAlive()) { thread.interrupt();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread.isAlive()) { thread.interrupt();}
    }
}

