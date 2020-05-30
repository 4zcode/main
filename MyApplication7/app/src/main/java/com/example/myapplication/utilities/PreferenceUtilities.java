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
import com.example.myapplication.doctors.speciality.DBManagerSpeciality;
import com.example.myapplication.message.DBManagerMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public static final String FIRST_TIME = "firstTime";
    public static final String SPECIALITY = "Speciality";


    private static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference UsersRef = rootRef.child("Users");
    private static DatabaseReference MessageNonReadRef = rootRef.child("Messages");
    private static DatabaseReference specRef = rootRef.child("Specialities");



    private static Integer count = new Integer(0);


    synchronized public static void saveUserInfo(final Context context, final Boolean isLogin) {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UsersRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean mContinue = dataSnapshot.child("UserName").exists() && dataSnapshot.child("UserImageUrl").exists() && dataSnapshot.child("UserType").exists();

                    if (mContinue) {
                        String Name = dataSnapshot.child("UserName").getValue(String.class);
                        String ImageUrl = dataSnapshot.child("UserImageUrl").getValue(String.class);
                        String Type = dataSnapshot.child("UserType").getValue(String.class);
                        CheckIfUserProfileComplete(context, Name, Type, ImageUrl);
                        getNbrMessageNoRead(context);
                    } else {
                        saveData(context, DEFAULT_USER_NAME, DEFAULT_USER_IMAGE, DEFAULT_USER_TYPE, true, false);
                        AlertDialog message = new AlertDialog.Builder(context)
                                .setTitle("Reminder")
                                .setMessage("You don't complete your profil !")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context, Insertion.class));
                                    }
                                })
                                .setNegativeButton("Later", null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "error : " + databaseError.getMessage());
                }
            });
        } else {
            saveData(context, DEFAULT_USER_NAME, DEFAULT_USER_IMAGE, DEFAULT_USER_TYPE, false, false);
        }
    }
    public static void initialSpecDB(Context context){
        final DBManagerSpeciality db = new DBManagerSpeciality(context);
        db.open();
        final Integer[] count = {0};
           String[] array = context.getResources().getStringArray(R.array.speciality);
           specRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for (DataSnapshot ds : dataSnapshot.getChildren()){
                       if (db.CheckIsDataAlreadyInDBorNot(ds.getKey())){
                          db.update(count[0],ds.getKey(),ds.getValue(String.class));
                       }else {
                           db.insert(count[0],ds.getKey(),ds.getValue(String.class));
                       }
                       count[0]++;
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Log.d("specialityTest","error "+ databaseError.getMessage());

               }
           });
    }

    public static void CheckIfUserProfileComplete(Context context, String UserName, String UserType, String UserImage) {

        String childRef = "";
        switch (UserType) {
            case "Doctor":
                childRef = "Doctor";
                break;
            case "Pharmacie":
                childRef = "pharmacies";
                break;
            case "Hospital":
                childRef = "Hopital";
                break;
            case "Laboratoir":
                childRef = "laboratoir";
                break;
            case "Normal":
                childRef = "Normal";
                break;
        }
        if (rootRef.child(childRef).child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())) != null) {
            saveData(context, UserName, UserImage, UserType, true, true);
            Toast.makeText(context, "Profile Exist", Toast.LENGTH_LONG).show();

        } else {
            saveData(context, UserName, UserImage, UserType, true, false);
            Toast.makeText(context, "Profile not exist", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, Insertion.class));
        }

    }


    public static void saveData(Context context, String userName, String userImage, String userType, boolean isLogin, boolean isProfileExist) {
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

    public static boolean firstTime(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (Pref.getBoolean(FIRST_TIME, true)) {
            SharedPreferences.Editor editor = Pref.edit();
            editor.putBoolean(FIRST_TIME, false);
            editor.apply();
            return true;
        } else return false;
    }

    public static void saveSpeciality(Context context, String speciality) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putString(SPECIALITY, speciality);
        editor.apply();
    }

    public static String getLastSpeciality(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Pref.getString(SPECIALITY, "Médecin généraliste");

    }

    public static void getNbrMessageNoRead(final Context context) {
        final DBManagerMessage db;
        db = new DBManagerMessage(context);


        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = Pref.edit();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            MessageNonReadRef.child(String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    db.open();
                    db.deleteAll();
                    count = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        boolean is_exist = ds.child("message_envoyer").exists() && ds.child("Sender_Name").exists() && ds.child("ID_Reciver").exists() && ds.child("Is_Readed").exists() && ds.child("Date").exists() && ds.child("AllMsg").exists();
                        if (is_exist) {
                            String SenderName = ds.child("Sender_Name").getValue(String.class);
                            String SenderImage;
                            if (ds.child("Sender_Image").exists()) {
                                SenderImage = ds.child("Sender_Image").getValue(String.class);
                            } else SenderImage = "R.drawable.doctorm";
                            String message = ds.child("message_envoyer").getValue(String.class);
                            String fullMsg = ds.child("AllMsg").getValue(String.class);
                            String ID_firebase = ds.child("ID_Reciver").getValue(String.class);
                            String Is_Readed = ds.child("Is_Readed").getValue(String.class);
                            if (Is_Readed.equals("false")) {
                                count += 1;
                            }
                            String Date = ds.child("Date").getValue(String.class);
                            if (!ID_firebase.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (db.CheckIsDataAlreadyInDBorNot(ID_firebase)) {
                                    db.update(ID_firebase, SenderName, message, fullMsg, Date, Is_Readed, SenderImage);
                                } else {
                                    db.insert(ID_firebase, SenderName, message, fullMsg, Date, Is_Readed, SenderImage);
                                }
                            }
                        }
                    }
                    if (!getNbrMsgs(context).equals(String.valueOf(count))) {
                        editor.putString(KEY_NUMBER_MESSAGES_NON_READ, String.valueOf(count));
                        editor.apply();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d(TAG, "erorr : " + databaseError.getMessage());
                }
            });
        } else {
            editor.putString(KEY_NUMBER_MESSAGES_NON_READ, DEFAULT_MESSAGE);
            editor.apply();
        }
    }

    public static String getNbrMsgs(Context context) {
        SharedPreferences Pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return Pref.getString(KEY_NUMBER_MESSAGES_NON_READ, DEFAULT_MESSAGE);
    }
}
