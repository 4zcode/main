package com.example.myapplication.Hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class HospitalProfileActivity extends AppCompatActivity {
    public static final String ID = "id";

    private TextView nameView,desView, phoneView, serviceView, locationView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_profile);
        imageView = findViewById(R.id.activity_hopital_image);
        phoneView = findViewById(R.id.phon);
        serviceView = findViewById(R.id.serv);
        desView = findViewById(R.id.desc);
        locationView = findViewById(R.id.location);
        initialView();
       /* servview.setText(getIntent().getStringExtra("SERV_KEY"));
        phonview.setText(getIntent().getStringExtra("PHON_KEY"));
        descview.setText(getIntent().getStringExtra("DESC_KEY"));
*/


    }

    private void initialView() {
        DBManagerHospital db = new DBManagerHospital(getBaseContext());
        db.open();
        Hopital currentHopital = db.getHopitalFromID(getIntent().getIntExtra(ID,1));
        db.close();
        Glide.with(getBaseContext()).load(currentHopital.getImageResource()).into(imageView);
        phoneView.setText(currentHopital.getHopitalContact());
        serviceView.setText(currentHopital.getHopitalservice());
        desView.setText(currentHopital.getHopitaldescription());
        locationView.setText(currentHopital.getHopitalPlace());
        this.setTitle(currentHopital.getHopitalName());
    }
}
