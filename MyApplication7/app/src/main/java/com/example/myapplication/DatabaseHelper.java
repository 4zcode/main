package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.apache.commons.lang3.text.WordUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    // Database Information
    static final String DB_NAME = "SahtiFiYdi.db";

    // database version
    static final int DB_VERSION = 35;


    // Table Name
    public static final String TABLE_NAME_PHARMACIE = "Pharmacies";
    public static final String TABLE_NAME_HOSPITAL = "hospital";
    public static final String TABLE_NAME_DOCTORS = "doctors";
    public static final String TABLE_NAME_SPECIALITY = "speciality";
    public static final String TABLE_NAME_LABORATOIR = "laboratoir";
    public static final String TABLE_NAME_MESSAGES = "messages";
    public static final String TABLE_NAME_DONATEUR = "donateurs";

    // Table pharmacies columns
    public static final String _ID_PHARMA_FIREBASE = "_id_pharmacies_firebase";
    public static final String NAME_PHARMA = "name";
    public static final String PLACE_PHARMA = "place";
    public static final String IMAGE_PHARMA_URL ="image" ;
    public static final String PHONE_PHARMA = "phone";
    public static final String TIME = "time";
    public static final String DESCRIPTION="description";

    public static final String WILAYA = "wilaya";
    public static final String COMMUNE = "commune";




    // Table Hopital columns
    public static final String _ID_HOSPITAL_FIREBASE = "_id_hospital_firebase";
    public static final String NAME__HOSPITAL = "name";
    public static final String PLACE__HOSPITAL = "place";
    public static final String TYPE__HOSPITAL = "type";
    public static final String SERVICE__HOSPITAL = "service";
    public static final String DESCRIPTION__HOSPITAL = "description";
    public static final String NUMBER__HOSPITAL = "number";
    public static final String IMAGE_HOSPITAL_URL ="image" ;

    // Table doctor columns
    public static final String _ID_DOCTOR_FIREBASE = "_id_doctor_firebase";
    public static final String NAME_DOCTOR = "name";
    public static final String PLACE_DOCTOR = "place";
    public static final String PHONE_DOCTOR = "phone";
    public static final String SPEC_DOCTOR = "specaility";
    public static final String DOCTOR_SERVICE = "service";
    public static final String DOCTOR_TYPE = "type";
    public static final String DOCTOR_TIME = "time";
    public static final String IMAGE_DOCTOR_URL ="image" ;


    // Table doctor specialité columns
    public static final String SPECIALITY_NAME = "name";
    public static final String SPECIALITY_NUMBER = "number";



    //Table message columns
    public static final String _ID_MESSAGE_SENDER_FIREBASE="_id_message_sender_firebase";
    public static final String SENDER_MESSAGE_NAME ="sender_name_message";
    public static final String RECENT_MESSAGE ="recent_message";
    public static final String MESSAGE_RECENT_DATE ="message_recent_date";
    public static final String FULL_MESSAGE ="full_message";
    public static final String IS_READ ="is_read";
    public static final String IMAGE_SENDER_MESSAGE_URL ="image_sender_message_url";

    // Table Donateur columns
    public static final String _ID_DONATEUR_FIREBASE = "_id_donateurs_firebase";
    public static final String NAME_DONATEUR = "name";
    public static final String PLACE_DONATEUR = "place";
    public static final String PHONE_DONATEUR = "phone";
    public static final String AGE="age";
    public static final String IMAGE_DONATEUR_URL ="image" ;
    public static final String GRSANGUIN_DONATEUR = "groupsanguin";



    // Creating table query
    private static final String CREATE_TABLE_PHARMACIE = "create table " + TABLE_NAME_PHARMACIE + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _ID_PHARMA_FIREBASE + " TEXT UNIQUE NOT NULL, "
            + NAME_PHARMA + " TEXT NOT NULL, "
            + PLACE_PHARMA + " TEXT, "
            + WILAYA + " INTEGER, "
            + COMMUNE + " INTEGER, "
            + PHONE_PHARMA+ " TEXT, "
            + TIME + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + IMAGE_PHARMA_URL +" TEXT );";

    public static final String INDEX_PHAR = TABLE_NAME_PHARMACIE + "_indexphar";
    public static final String SQL_CREATE_INDEX_PHAR =
            "CREATE INDEX " + INDEX_PHAR + " ON " + TABLE_NAME_PHARMACIE +
                    "(" + _ID_PHARMA_FIREBASE + ")";




    private static final String CREATE_TABLE_HOSPITAL = "create table " + TABLE_NAME_HOSPITAL + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _ID_HOSPITAL_FIREBASE+" TEXT  UNIQUE NOT NULL, "
            + NAME__HOSPITAL + " TEXT NOT NULL, "
            + DESCRIPTION__HOSPITAL + " TEXT, "
            + PLACE__HOSPITAL + " TEXT, "
            + WILAYA + " INTEGER, "
            + COMMUNE + " INTEGER, "
            + TYPE__HOSPITAL + " INTEGER, "
            + NUMBER__HOSPITAL + " TEXT UNIQUE, "
            + SERVICE__HOSPITAL + " TEXT, "
            + IMAGE_HOSPITAL_URL+" TEXT );";

    public static final String INDEX_HOS = TABLE_NAME_HOSPITAL + "_indexhos";
    public static final String SQL_CREATE_INDEX_HOPITAL =
            "CREATE INDEX " + INDEX_HOS + " ON " + TABLE_NAME_HOSPITAL +
                    "(" + _ID_HOSPITAL_FIREBASE + ")";



    private static final String CREATE_TABLE_LABORATOIR = "create table " + TABLE_NAME_LABORATOIR + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _ID_HOSPITAL_FIREBASE+" TEXT  UNIQUE NOT NULL, "
            + NAME__HOSPITAL + " TEXT NOT NULL, "
            + DESCRIPTION__HOSPITAL + " TEXT, "
            + PLACE__HOSPITAL + " TEXT, "
            + WILAYA + " INTEGER, "
            + COMMUNE + " INTEGER, "
            + TYPE__HOSPITAL + " INTEGER, "
            + NUMBER__HOSPITAL + " TEXT UNIQUE, "
            + SERVICE__HOSPITAL + " TEXT, "
            + IMAGE_HOSPITAL_URL+" TEXT );";


    public static final String INDEX_LAB = TABLE_NAME_HOSPITAL + "_indexlab";
    public static final String SQL_CREATE_INDEX_LABO =
            "CREATE INDEX " + INDEX_LAB + " ON " + TABLE_NAME_LABORATOIR +
                    "(" + _ID_HOSPITAL_FIREBASE + ")";


    private static final String CREATE_TABLE_DOCTORS = "create table " + TABLE_NAME_DOCTORS + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _ID_DOCTOR_FIREBASE + " TEXT UNIQUE NOT NULL, "
            + NAME_DOCTOR + " TEXT NOT NULL, "
            + PLACE_DOCTOR + " TEXT, "
            + WILAYA + " INTEGER, "
            + COMMUNE + " INTEGER, "
            + PHONE_DOCTOR+ " TEXT, "
            + SPEC_DOCTOR+" TEXT NOT NULL, "
            + DOCTOR_TYPE +" TEXT, "
            + DOCTOR_SERVICE+" TEXT, "
            + DOCTOR_TIME+" TEXT, "
            + IMAGE_DOCTOR_URL + " TEXT );";

    public static final String INDEX_DOC = TABLE_NAME_HOSPITAL + "_indexdoc";
    public static final String SQL_CREATE_INDEX_DOCTOR =
            "CREATE INDEX " + INDEX_DOC + " ON " + TABLE_NAME_DOCTORS +
                    "(" + _ID_DOCTOR_FIREBASE + ")";


    private static final String CREATE_TABLE_MESSAGES ="create table "+TABLE_NAME_MESSAGES+"("
     + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
     + _ID_MESSAGE_SENDER_FIREBASE+ " TEXT UNIQUE NOT NULL, "
     + SENDER_MESSAGE_NAME+ " TEXT NOT NULL, " 
     + RECENT_MESSAGE+ " TEXT, "
     + FULL_MESSAGE + " TEXT ,"
     + MESSAGE_RECENT_DATE+ " TEXT, "
     + IS_READ + " TEXT ,"
     + IMAGE_SENDER_MESSAGE_URL+" TEXT);";
  
    public static final String INDEX_MES = TABLE_NAME_MESSAGES + "_indexmes";
    public static final String SQL_CREATE_INDEX_MESSAGE =
            "CREATE INDEX " + INDEX_MES + " ON " + TABLE_NAME_MESSAGES +
                    "(" + _ID_MESSAGE_SENDER_FIREBASE + ")";



    private static final String CREATE_TABLE_DONATEUR = "create table " + TABLE_NAME_DONATEUR + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +_ID_DONATEUR_FIREBASE + " TEXT UNIQUE NOT NULL, "
            + NAME_DONATEUR + " TEXT NOT NULL, "
            + PLACE_DONATEUR + " TEXT, "
            + WILAYA + " INTEGER, "
            + COMMUNE + " INTEGER, "
            + PHONE_DONATEUR+ " TEXT, "
            + AGE + " TEXT NOT NULL, "
            + GRSANGUIN_DONATEUR + " TEXT, "
            + IMAGE_DONATEUR_URL+" TEXT );";

    public static final String INDEX_DON = TABLE_NAME_DONATEUR + "_indexdon";
    public static final String SQL_CREATE_INDEX_DONATION =
            "CREATE INDEX " + INDEX_DON + " ON " + TABLE_NAME_DONATEUR +
                    "(" + _ID_DONATEUR_FIREBASE + ")";

 private static final String CREATE_TABLE_SPECIALITY = "create table " + TABLE_NAME_SPECIALITY + "(" 
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
            + SPECIALITY_NAME + " TEXT UNIQUE NOT NULL, "
            + SPECIALITY_NUMBER+" TEXT );";

    public static final String INDEX_SPEC = TABLE_NAME_SPECIALITY + "_indexspec";
    public static final String SQL_CREATE_INDEX_SPEC =
            "CREATE INDEX " + INDEX_SPEC + " ON " + TABLE_NAME_SPECIALITY +
                    "(" + SPECIALITY_NAME + ")";




    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PHARMACIE);
        db.execSQL(CREATE_TABLE_HOSPITAL);
        db.execSQL(CREATE_TABLE_DOCTORS);
        db.execSQL(CREATE_TABLE_SPECIALITY);
        db.execSQL(CREATE_TABLE_LABORATOIR);
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_DONATEUR);

        db.execSQL(SQL_CREATE_INDEX_DOCTOR);
        db.execSQL(SQL_CREATE_INDEX_PHAR);
        db.execSQL(SQL_CREATE_INDEX_HOPITAL);
        db.execSQL(SQL_CREATE_INDEX_LABO);
        db.execSQL(SQL_CREATE_INDEX_SPEC);
        db.execSQL(SQL_CREATE_INDEX_MESSAGE);
        db.execSQL(SQL_CREATE_INDEX_DONATION);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PHARMACIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOSPITAL);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_SPECIALITY);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_LABORATOIR);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DONATEUR);
        onCreate(db);
    }

}
