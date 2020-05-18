package com.example.myapplication.Profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_IMAGE;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_NAME;
import static com.example.myapplication.utilities.PreferenceUtilities.KEY_USER_TYPE;
import static com.example.myapplication.utilities.PreferenceUtilities.PREFERENCE_NAME;

public class UserProfile extends AppCompatActivity {
    private ImageView user_image;
    private TextView user_name, user_spec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        user_spec = findViewById(R.id.user_speciality);


        SharedPreferences Pref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        user_name.setText(Pref.getString(KEY_USER_NAME,"Akram Bensalem"));
        user_spec.setText(Pref.getString(KEY_USER_TYPE,"Cardiologue"));

        Glide.with(this).load(Pref.getString(KEY_USER_IMAGE,"R.drawable.profile")).into(user_image);

    }
}
