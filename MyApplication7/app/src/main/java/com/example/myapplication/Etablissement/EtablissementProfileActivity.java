package com.example.myapplication.Etablissement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

public class EtablissementProfileActivity extends AppCompatActivity {
    public static final String ID = "id";
    public static final String TYPE = "type";
    private TextView desView, phoneView, serviceView, locationView;
    private ImageView imageView;
    private String mTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablissement_profile);



        imageView = findViewById(R.id.activity_etablisment_image);
        phoneView = findViewById(R.id.activity_etablisment_contact);
        serviceView = findViewById(R.id.activity_etablisment_service);
        desView = findViewById(R.id.activity_etablisment_description);
        locationView = findViewById(R.id.activity_etablisment_location);



        initialView();


        // animate
        desView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
        serviceView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
        phoneView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));
        locationView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.push_up_in));

    }

    private void initialView() {

        Intent i = getIntent();
        mTableName = i.getIntExtra(TYPE,1) == 0 ? DatabaseHelper.TABLE_NAME_HOSPITAL : DatabaseHelper.TABLE_NAME_LABORATOIR;
        DBManagerEtablissement db = new DBManagerEtablissement(getBaseContext());
        db.open(mTableName);
        Etablissement currentEtablissement = db.getHopitalFromID(i.getIntExtra(ID,1));
        db.close();

        String imageUrl = currentEtablissement.getImageResource();
        if (imageUrl.equals("R.drawable.profile")){
            Glide.with(this).load(R.drawable.profile).into(imageView);

        }else {
            Glide.with(this).load(imageUrl).into(imageView);
        }
        phoneView.setText(currentEtablissement.getHopitalContact());
        serviceView.setText(currentEtablissement.getHopitalservice());
        desView.setText(currentEtablissement.getHopitaldescription());
        locationView.setText(currentEtablissement.getHopitalPlace());
        this.setTitle(currentEtablissement.getHopitalName());
    }
}
