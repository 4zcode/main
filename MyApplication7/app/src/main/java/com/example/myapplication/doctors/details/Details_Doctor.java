package com.example.myapplication.doctors.details;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.message.chatRoom.chatRoom;
import com.example.myapplication.ui.login.Signin;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.lang3.text.WordUtils;

import static com.example.myapplication.utilities.tools.isNetworkAvailable;

public class Details_Doctor extends AppCompatActivity {
    //Les constants
    public static final String FIREBASE_ID = "firebaseId";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String PHONE = "PHONE";


    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";


    private TabLayout tabLayout;
    private ViewPager viewPager;


    private ImageView DoctorImage;
    private TextView DoctorName;

    private ImageButton bPhone, bMessage, bStar;
    private RatingBar star;
    private String mFireBaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__doctor);
        tabLayout = (TabLayout) findViewById(R.id.details_doctor_tablayout);
        viewPager = (ViewPager) findViewById(R.id.details_doctor_viewpager);
        DoctorImage = (ImageView) findViewById(R.id.details_doctor_image);
        DoctorName = (TextView) findViewById(R.id.details_doctor_Name);
        bPhone = (ImageButton) findViewById(R.id.details_doctor_phone);
        bMessage = (ImageButton) findViewById(R.id.details_doctor_messsage);
        star = (RatingBar) findViewById(R.id.details_doctor_star);
      //  bStar = (ImageButton) findViewById(R.id.details_doctor_start);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //
        bPhone.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fall_down_retard));
        bMessage.setAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fall_down_retard));


        //
        DetailsDoctorTablayoutAdapter myAdapter = new DetailsDoctorTablayoutAdapter(this, getSupportFragmentManager(), 2);
        viewPager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPager);


        initialView();


        //
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_outline_black_24dp);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_person_pin_circle_black_24dp);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //
        bPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //téléphoner
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getIntent().getStringExtra(PHONE).replaceAll(" ","")));
                startActivity(i);
            }
        });
        bMessage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //aller aux chat message
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent i = new Intent(getBaseContext(), chatRoom.class);
                    i.putExtra(RECIVER, mFireBaseId);
                    i.putExtra(RECIVER_IMAGE, "R.drawable.profile");
                    i.putExtra(SENDER, DoctorName.getText().toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.open_enter, R.anim.nav_default_exit_anim);
                }else {
                    //
                    new AlertDialog.Builder(getBaseContext())
                            .setTitle("Service non disponible")
                            .setMessage("Vous devez vous connecter d'abord!")
                            .setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getBaseContext(), Signin.class));
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .setIcon(R.drawable.ic_do_not_disturb_alt_black_24dp)
                            .show();
                }
            }
        });

          star.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (star.getRating()==1){
                      star.setRating(0);
                  }else{
                      star.setRating(1);
                  }
              }
          });
     /*   bStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bStar.setImageResource(R.drawable.ic_star_black_24dp);
            }
        });

      */


    }


    private void initialView() {
        Intent i = getIntent();
        mFireBaseId = i.getStringExtra(FIREBASE_ID);
        Glide.with(getBaseContext()).load(R.drawable.profile).into(DoctorImage);
        DoctorName.setText(WordUtils.capitalizeFully(i.getStringExtra(NAME)));
    }

    @Override
    public void onBackPressed() {
        ViewPager viewpager = (ViewPager) findViewById(R.id.details_doctor_viewpager);
        if (viewpager.getCurrentItem() == 0) super.onBackPressed();
        else viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }
  /*  @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isNetworkAvailable(getBaseContext())){
            menu.getItem(0).setIcon(R.drawable.ic_sync_24dp);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_sync_problem_black_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

   */
   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.nav_home){
           onBackPressed();
       }else if(id == R.id.block_icon){
            if (isNetworkAvailable(getBaseContext())){
               //
                new AlertDialog.Builder(this)
                        .setTitle("Avertissement")
                        .setMessage("Vouillez vous vraiment bloquer cette personne ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(),"Blocked",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(R.drawable.ic_perm_scan_wifi_black_24dp)
                        .show();

            }else {
                Toast.makeText(getBaseContext(),"un preblem avec connexion",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
