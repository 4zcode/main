package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME_PHARMACIE = "Pharmacies";
    public static final String TABLE_NAME_HOSPITAL = "hospital";
    public static final String TABLE_NAME_DOCTORS = "doctors";


    // Table columns
    public static final String _ID_PHARMA = "_id";
    public static final String NAME_PHARMA = "name";
    public static final String PLACE_PHARMA = "place";
    public static final String _ID_HOSPITAL = "_id";
    public static final String NAME__HOSPITAL = "name";
    public static final String PLACE__HOSPITAL = "place";
    public static final String NUMBER__HOSPITAL = "number";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String _ID_DOCTOR = "_id";
    public static final String _ID_DOCTOR_FIREBASE = "_id_doctor_firebase";
    public static final String NAME_DOCTOR = "name";
    public static final String PLACE_DOCTOR = "place";
    public static final String PHONE_DOCTOR = "phone";
    public static final String SPEC_DOCTOR = "specaility";
    public static final String SEX_DOCTOR = "sex";

    // Database Information
    static final String DB_NAME = "SahtiFiYdi.db";

    // database version
    static final int DB_VERSION = 5;

    // Creating table query
    private static final String CREATE_TABLE_PHARMACIE = "create table " + TABLE_NAME_PHARMACIE + "(" + _ID_PHARMA
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_PHARMA + " TEXT NOT NULL, " + PLACE_PHARMA + " TEXT, " + OPEN + " TEXT, " + CLOSE + " TEXT);";
    private static final String CREATE_TABLE_HOSPITAL = "create table " + TABLE_NAME_HOSPITAL + "(" + _ID_HOSPITAL
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME__HOSPITAL + " TEXT NOT NULL, " + PLACE__HOSPITAL + " TEXT, " + NUMBER__HOSPITAL + " TEXT);";
    private static final String CREATE_TABLE_DOCTORS = "create table " + TABLE_NAME_DOCTORS + "(" + _ID_DOCTOR
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + _ID_DOCTOR_FIREBASE + " TEXT NOT NULL, " + NAME_DOCTOR + " TEXT NOT NULL, " + PLACE_DOCTOR + " TEXT, " + PHONE_DOCTOR+ " TEXT, "+SPEC_DOCTOR+" TEXT NOT NULL, "+SEX_DOCTOR+" TEXT);";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PHARMACIE);
        db.execSQL(CREATE_TABLE_HOSPITAL);
        db.execSQL(CREATE_TABLE_DOCTORS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PHARMACIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOSPITAL);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_DOCTORS);
        onCreate(db);
    }
}
