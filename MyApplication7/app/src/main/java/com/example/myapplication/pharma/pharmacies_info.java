package com.example.myapplication.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class pharmacies_info extends AppCompatActivity implements OnMapReadyCallback {
    public GoogleMap map;
    public ImageView image;
    public TextView contact;
    public TextView location;
    public TextView open;
    public TextView close;
    public TextView day;
    public TextView description;
    public Intent intent;
    public GradientDrawable mGradientDrawable;
    private static final int REQUEST_CALL = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies_info);
        Log.d("test_ph","2.0");
        SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapph);
        supportMapFragment.getMapAsync(this);
        intent=getIntent();
        description=(TextView) findViewById(R.id.description_ph_info);
        image=(ImageView) findViewById(R.id.image_ph_info);
        contact=(TextView) findViewById(R.id.contact_ph_info);
        location=(TextView) findViewById(R.id.location_ph_info);
        Log.d("test_ph","2.1");
        ArrayList<time_class> listetime=new ArrayList<time_class>();
        Log.d("test_ph","2.2"+intent.getStringExtra("time"));
        listetime=getListetime(intent.getStringExtra("time_ph"));
        Log.d("test_ph","2.3"+listetime.size());
        if(listetime.size()==1){
            Log.d("test_ph","1size");
            day=(TextView) findViewById(R.id.dimanche_ph);
            open=(TextView) findViewById(R.id.open_ph_info_dimanche);
            close=(TextView) findViewById(R.id.close_ph_info_dimanche);
            day.setText(listetime.get(0).alltime);
            open.setVisibility(View.GONE);
            close.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < listetime.size(); i++) {
                Log.d("test_ph",listetime.get(i).day+listetime.get(i).open+listetime.get(i).close+listetime.get(i).alltime);
                switch (i) {
                    case 0:
                        day = (TextView) findViewById(R.id.dimanche_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_dimanche);
                        close = (TextView) findViewById(R.id.close_ph_info_dimanche);
                        Log.d("test_ph", String.valueOf(i));
                        break;
                    case 1:
                        day = (TextView) findViewById(R.id.lundi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_lundi);
                        close = (TextView) findViewById(R.id.close_ph_info_lundi);
                        Log.d("test_ph", String.valueOf(i));
                        break;
                    case 2:
                        day = (TextView) findViewById(R.id.mardi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_mardi);
                        close = (TextView) findViewById(R.id.close_ph_info_mardi);
                        break;
                    case 3:
                        day = (TextView) findViewById(R.id.mercredi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_mercredi);
                        close = (TextView) findViewById(R.id.close_ph_info_mercredi);
                        break;
                    case 4:
                        day = (TextView) findViewById(R.id.jeudi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_jeudi);
                        close = (TextView) findViewById(R.id.close_ph_info_jeudi);
                        break;
                    case 5:
                        day = (TextView) findViewById(R.id.vendredi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_vendredi);
                        close = (TextView) findViewById(R.id.close_ph_info_venderdi);
                        break;
                    case 6:
                        day = (TextView) findViewById(R.id.samedi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_samedi);
                        close = (TextView) findViewById(R.id.close_ph_info_samedi);
                        break;
                }
                if (listetime.get(i).alltime.equals("@null")) {
                    Log.d("test_ph","2.4");
                    String da=listetime.get(i).day.substring(0,4);
                    day.setText( da+ ": ");
                    close.setText("closeat:" + listetime.get(i).close);
                    open.setText("openat:" + listetime.get(i).open);
                    day.setVisibility(View.VISIBLE);
                    open.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    Log.d("test_ph","2.5"+i);
                } else {
                    Log.d("test_ph","2.6");
                    day.setText(listetime.get(i).day + ": ");
                    open.setText(listetime.get(i).alltime);
                    Log.d("test_ph","2.7");
                }

            }
        }
        this.setTitle(intent.getStringExtra("name_ph"));
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Glide.with(this).load(intent.getStringExtra("imageUri"))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(mGradientDrawable)
                .into(image);
        contact.setText(intent.getStringExtra("contact_ph"));
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        location.setText(intent.getStringExtra("adress_ph"));
        Log.d("test_ph","2.8"+intent.getStringExtra("description"));
        description.setText(intent.getStringExtra("description"));

    }

    public void map_ph_info(View view) {
        Uri addressUri = Uri.parse("geo:0,0?q=" + intent.getStringExtra("adress_ph"));
        Intent intent1 = new Intent(Intent.ACTION_VIEW, addressUri);
        startActivity(intent1);

    }

    public void txt_ph_info(View view) {
        SharedPreferences myPef;
        myPef =this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        if (myPef.getBoolean("IsLogIn", false) && FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser user1 = FirebaseAuth.getInstance().getInstance().getCurrentUser();
            if (!user1.getUid().equals(intent.getStringExtra("idfirebase"))) {
                Intent intent2 = pharmacy.starter(this,intent.getStringExtra("idfirebase"), intent.getStringExtra("name_ph"));
                this.startActivity(intent2);
            } else {
                Toast.makeText(this, "you cant send to your self", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void makePhoneCall() {
        String number = intent.getStringExtra("contact_ph");
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public ArrayList<time_class> getListetime(String time){
        String[] fulltime = time.split("@123brtest");
        Log.d("test_ph",fulltime.toString());
        ArrayList<time_class>arrayList=new ArrayList<>();
        if(fulltime.length==1){arrayList.add(new time_class("@null","@null","@null",fulltime[0]));}
         else{
                  for (String petitMsg : fulltime) {
                      String[] detail = petitMsg.split("<br>");
                      Log.d("test_ph",detail.toString());
                      if (detail.length == 2) {
                       arrayList.add(new time_class(detail[0], "@null", "@null", detail[1]));
                                               }else{
                                                       if(detail.length==3){
                                                        arrayList.add(new time_class(detail[0], detail[1], detail[2],"@null"));
                                                                           }
                                                     }

                  }
             }
         return arrayList;
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng latLng =new LatLng(33.692064, 1.016893);
        map.addMarker(new MarkerOptions().position(latLng).title("darna"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}
