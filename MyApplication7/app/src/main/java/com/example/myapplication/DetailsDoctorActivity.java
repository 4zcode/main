package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DetailsDoctorActivity extends AppCompatActivity {
    private ImageView user_image;
    private TextView user_name, user_spec,user_mail,user_type,user_phone;
    private RatingBar user_rating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detils_doctor);
        user_image = findViewById(R.id.doctor_details_image);
        user_name = findViewById(R.id.doctor_details_name);
        user_spec = findViewById(R.id.doctor_details_specialiy);
        user_mail = findViewById(R.id.doctor_details_email);
        user_type = findViewById(R.id.doctor_details_type);
        user_phone= findViewById(R.id.doctor_details_phone);
        user_rating= findViewById(R.id.doctor_details_ratting);


        Intent newIntent = getIntent();
       // newIntent.getStr

    }




    public void callMe(View view) {
    }

    public void mapMe(View view) {
    }
}
