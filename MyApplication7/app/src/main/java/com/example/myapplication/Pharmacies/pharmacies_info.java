package com.example.myapplication.Pharmacies;

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
import com.example.myapplication.Pharmacies.time_class;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.text.WordUtils;

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
    private String mName,mPhone ,mPlace, mImage, mDescription, mTime;
    private pharmacy mCurrentPharmacy;

    private static final int REQUEST_CALL = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies_info);

        SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapph);
        supportMapFragment.getMapAsync(this);

        //
        description=(TextView) findViewById(R.id.description_ph_info);
        image=(ImageView) findViewById(R.id.image_ph_info);
        contact=(TextView) findViewById(R.id.contact_ph_info);
        location=(TextView) findViewById(R.id.location_ph_info);


        initializeView();




        ArrayList<time_class> listetime=new ArrayList<time_class>();
        listetime=getListetime(mTime);

        if(listetime.size()==1){
            day=(TextView) findViewById(R.id.dimanche_ph);
            open=(TextView) findViewById(R.id.open_ph_info_dimanche);
            close=(TextView) findViewById(R.id.close_ph_info_dimanche);
            day.setText(listetime.get(0).alltime);
            open.setVisibility(View.GONE);
            close.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < listetime.size(); i++) {
                switch (i) {
                    case 0:
                        day = (TextView) findViewById(R.id.dimanche_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_dimanche);
                        close = (TextView) findViewById(R.id.close_ph_info_dimanche);
                        break;
                    case 1:
                        day = (TextView) findViewById(R.id.lundi_ph);
                        open = (TextView) findViewById(R.id.open_ph_info_lundi);
                        close = (TextView) findViewById(R.id.close_ph_info_lundi);
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
                    String da=listetime.get(i).day.substring(0,4);
                    day.setText( da+ ": ");
                    close.setText("closeat:" + listetime.get(i).close);
                    open.setText("openat:" + listetime.get(i).open);
                    day.setVisibility(View.VISIBLE);
                    open.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                } else {
                    day.setText(listetime.get(i).day + ": ");
                    open.setText(listetime.get(i).alltime);
                }

            }
        }








        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });


    }

    private void initializeView() {
        intent=getIntent();
    /*    mName = intent.getStringExtra("name_ph");
        mPlace = intent.getStringExtra("adress_ph");
        mImage = intent.getStringExtra("imageUri");
        mDescription = intent.getStringExtra("description");
        mPhone = intent.getStringExtra("contact_ph");
        mTime = intent.getStringExtra("time_ph");
      */
        DBManagerPharmacy db = new DBManagerPharmacy(this);
        db.open();
        int id =  getIntent().getIntExtra("id",1);
        mCurrentPharmacy = db.getPharmacieFromID(id);
        mImage = mCurrentPharmacy.getImageUrl();
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        if (mImage.equals("R.drawable.profile")){
            Glide.with(this).load(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(mGradientDrawable)
                    .into(image);
        }else {
            Glide.with(this).load(mImage)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(mGradientDrawable)
                    .into(image);
        }
        mName = WordUtils.capitalizeFully(mCurrentPharmacy.getThename());
        this.setTitle(mName);

        mTime = mCurrentPharmacy.getTime();
        mPhone = mCurrentPharmacy.getPhone();
        mPlace = mCurrentPharmacy.getTheadress();
        mDescription = mCurrentPharmacy.getDescription();


        location.setText(mPlace);
        description.setText(mDescription);
        contact.setText(mPhone);

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
