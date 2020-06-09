package com.example.myapplication.don_sang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class donateurinfo extends AppCompatActivity {
    public ImageView image;
    public TextView contact;
    public TextView location;
    public TextView description;
    public Intent intent;
    public GradientDrawable mGradientDrawable;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donateurinfo);
        intent=getIntent();
        description=(TextView) findViewById(R.id.description_donateur_info);
        image=(ImageView) findViewById(R.id.donateur_image_info);
        contact=(TextView) findViewById(R.id.contact_donateur_info);
        location=(TextView) findViewById(R.id.location_donateur_info);
        description.setText("I'am "+intent.getStringExtra("name_donateur")+", i'm "+intent.getStringExtra("age_donateur")+" years old and i have "+intent.getStringExtra("grsanguin_donateur")+" my blood group");
        contact.setText(intent.getStringExtra("phone_donateur"));
        location.setText(intent.getStringExtra("adress_donateur"));
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);
        Glide.with(this).load(intent.getStringExtra("image_donateur"))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(mGradientDrawable)
                .into(image);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

    }

    public void txt_donateur_info(View view) {
        SharedPreferences myPef;
        myPef =this.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        if (myPef.getBoolean("IsLogIn", false) && FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser user1 = FirebaseAuth.getInstance().getInstance().getCurrentUser();
            if (!user1.getUid().equals(intent.getStringExtra("idfirebase_donateur"))) {
                Intent intent2 = don_de_song.starter(this,intent.getStringExtra("idfirebase_donateur"), intent.getStringExtra("name_donateur"));
                this.startActivity(intent2);
            } else {
                Toast.makeText(this, "you cant send to your self", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void makePhoneCall() {
        String number = intent.getStringExtra("phone_donateur");
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
}
