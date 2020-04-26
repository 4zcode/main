package com.example.myapplication.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;
import com.example.myapplication.addProfile.Insertion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PreferenceUtilities {

    public static final String PREFERENCE_NAME = "userPref";


    // Les clés pour sauvgarder les information d'utilisateur

    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_IS_LOGIN = "IsLogIn";
    public static final String KEY_USER_IMAGE = "userImage";
    public static final String KEY_USER_TYPE = "userType";
    public static final String KEY_IS_PROFILE_EXIST = "IsProfileExist";
    public static final String KEY_NUMBER_MESSAGES_NON_READ = "NbrMessageNoRead";



    public static final String TAG = PreferenceUtilities.class.getSimpleName();



    public static final String DEFAULT_MESSAGE = "0";
    public static final String DEFAULT_USER_NAME = "Sahti fi yedi";
    public static final String DEFAULT_USER_TYPE = "NormalUser";
    public static final String DEFAULT_USER_IMAGE = String.valueOf(R.drawable.logo);






    private static  DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private  static DatabaseReference UsersRef = rootRef.child("Users");;
    private  static DatabaseReference MessageNonReadRef = rootRef.child("Message").child("ENVOYE");


    private static Integer count = new Integer(0);





    synchronized public static void saveUserInfo(final Context context, final Boolean isLogin) {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UsersRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean mContinue =dataSnapshot.child("UserName").exists()&& dataSnapshot.child("UserImageUrl").exists()&& dataSnapshot.child("UserType").exists();

                    if (mContinue) {
                        String Name = dataSnapshot.child("UserName").getValue(String.class);
                        String ImageUrl = dataSnapshot.child("UserImageUrl").getValue(String.class);
                        String Type = dataSnapshot.child("UserType").getValue(String.class);
                        saveData(context, Name, ImageUrl, Type, isLogin, true);
                        updateNbrMessagesNoRead(context);
                    }else {
                        saveData(context,DEFAULT_USER_NAME,DEFAULT_USER_IMAGE,DEFAULT_USER_TYPE,true,false);
                        AlertDialog message = new AlertDialog.Builder(context)
                                .setTitle("Reminder")
                                .setMessage("You don't complete your profil !")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       context.startActivity(new Intent(context, Insertion.class));
                                    }
                                })
                                .setNegativeButton("Later",null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG,"error : "+databaseError.getMessage());
                }
            });
        }else{
            saveData(context,DEFAULT_USER_NAME,DEFAULT_USER_IMAGE,DEFAULT_USER_TYPE,false,false);
        }

    }
    public static void saveData(Context context, String userName, String userImage, String userType, boolean isLogin, boolean isProfileExist){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_IMAGE, userImage);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.putBoolean(KEY_IS_PROFILE_EXIST, isProfileExist);
        if (isLogin && isProfileExist) {
            String nbrDeMSG = DEFAULT_MESSAGE;
            editor.putString(KEY_NUMBER_MESSAGES_NON_READ, nbrDeMSG);
        }
        editor.apply();
    }






    public static void getNbrMessageNoRead(final Context context){
        if (FirebaseAuth.getInstance().getCurrentUser()!= null ) {
            MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).child("Message_Non_Read").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = Pref.edit();
                    if (dataSnapshot.child("nbr_Messages").exists()) {
                        String NbrMsg = dataSnapshot.child("nbr_Messages").getValue(String.class);
                        editor.putString(KEY_NUMBER_MESSAGES_NON_READ, NbrMsg);
                        editor.apply();
                    } else {
                        editor.putString(KEY_NUMBER_MESSAGES_NON_READ, DEFAULT_MESSAGE);
                        editor.apply();
                    }
                  Log.d(TAG,Pref.getString(KEY_NUMBER_MESSAGES_NON_READ,DEFAULT_MESSAGE));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public static void updateNbrMessagesNoRead(final Context context){
        if (FirebaseAuth.getInstance().getCurrentUser()!= null ) {
            MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    count = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("Is_Readed").exists()) {
                            String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                            if (Is_Readed.equals("false")) {
                                count += 1;
                            }
                        }
                    }
                    Map<String, Object> user = new HashMap<String, Object>();
                    user.put("nbr_Messages", count.toString());
                    MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).child("Message_Non_Read").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                getNbrMessageNoRead(context);

                            } else {
                                String  error = task.getException().getMessage();
                                Toast.makeText(context.getApplicationContext(), "error : "+error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, databaseError.getMessage());
                }
            });
        }
    }

}
