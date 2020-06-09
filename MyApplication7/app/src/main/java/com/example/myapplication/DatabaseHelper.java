package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper<IMAGE_PHARMA_URL> extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME_PHARMACIE = "Pharmacies";
    public static final String TABLE_NAME_HOSPITAL = "hospital";
    public static final String TABLE_NAME_DOCTORS = "doctors";
    public static final String TABLE_NAME_LABORATOIR = "laboratoir";
    public static final String TABLE_NAME_DONATEUR = "donateurs";
    public static final String TABLE_NAME_MESSAGES = "messages";



    // Table columns
    public static final String _ID_DONATEUR = "_id";
    public static final String _ID_DONATEUR_FIREBASE = "_id_donateurs_firebase";
    public static final String NAME_DONATEUR = "name";
    public static final String PLACE_DONATEUR = "place";
    public static final String PHONE_DONATEUR = "phone";
    public static final String AGE="age";
    public static final String IMAGE_DONATEUR_URL ="image" ;
    public static final String GRSANGUIN_DONATEUR = "groupsanguin";
    public static final String _ID_PHARMA = "_id";
    public static final String _ID_PHARMA_FIREBASE = "_id_pharmacies_firebase";
    public static final String NAME_PHARMA = "name";
    public static final String PLACE_PHARMA = "place";
    public static final String IMAGE_PHARMA_URL ="image" ;
    public static final String PHONE_PHARMA = "phone";
    public static final String TIME = "time";
    public static final String DESCRIPTION="description";
    public static final String _ID_HOSPITAL = "_id";
    public static final String _ID_HOSPITAL_FIREBASE = "_id_hospital_firebase";
    public static final String NAME__HOSPITAL = "name";
    public static final String PLACE__HOSPITAL = "place";
    public static final String NUMBER__HOSPITAL = "number";
    public static final String IMAGE_HOSPITAL_URL ="image" ;
    public static final String _ID_LABORATOIR = "_id";
    public static final String _ID_LABORATOIR_FIREBASE = "_id_laboratoir_firebase";
    public static final String NAME__LABORATOIR = "name";
    public static final String PLACE__LABORATOIR = "place";
    public static final String NUMBER__LABORATOIR = "number";
    public static final String IMAGE_LABORATOIR_URL ="image" ;
    public static final String _ID_DOCTOR = "_id";
    public static final String _ID_DOCTOR_FIREBASE = "_id_doctor_firebase";
    public static final String NAME_DOCTOR = "name";
    public static final String PLACE_DOCTOR = "place";
    public static final String PHONE_DOCTOR = "phone";
    public static final String SPEC_DOCTOR = "specaility";
    public static final String SEX_DOCTOR = "sex";
    public static final String IMAGE_DOCTOR_URL ="image" ;
    public static final String _ID_MESSAGE = "_id";
    public static final String _ID_MESSAGE_SENDER_FIREBASE="_id_message_sender_firebase";
    public static final String SENDER_MESSAGE_NAME ="sender_name_message";
    public static final String RECENT_MESSAGE ="recent_message";
    public static final String MESSAGE_RECENT_DATE ="message_recent_date";
    public static final String FULL_MESSAGE ="full_message";
    public static final String IS_READ ="is_read";
    public static final String IMAGE_SENDER_MESSAGE_URL ="image_sender_message_url";
    public static final String IMAGE_MESSAGE ="image_message";


    // Database Information
    static final String DB_NAME = "SahtiFiYdi.db";

    // database version
    static final int DB_VERSION = 27;

    // Creating table query
    private static final String CREATE_TABLE_DONATEUR = "create table " + TABLE_NAME_DONATEUR + "(" + _ID_DONATEUR
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +_ID_DONATEUR_FIREBASE + " TEXT NOT NULL, "+ NAME_DONATEUR + " TEXT NOT NULL, " + PLACE_DONATEUR + " TEXT, " + PHONE_DONATEUR+ " TEXT, "+ AGE + " TEXT NOT NULL, " +GRSANGUIN_DONATEUR + " TEXT, " +IMAGE_DONATEUR_URL+" TEXT );";
    private static final String CREATE_TABLE_PHARMACIE = "create table " + TABLE_NAME_PHARMACIE + "(" + _ID_PHARMA
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +_ID_PHARMA_FIREBASE + " TEXT NOT NULL, "+ NAME_PHARMA + " TEXT NOT NULL, " + PLACE_PHARMA + " TEXT, " + PHONE_PHARMA+ " TEXT, "+TIME + " TEXT, " + DESCRIPTION + " TEXT, "+IMAGE_PHARMA_URL+" TEXT );";
    private static final String CREATE_TABLE_HOSPITAL = "create table " + TABLE_NAME_HOSPITAL + "(" + _ID_HOSPITAL
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + _ID_HOSPITAL_FIREBASE+" TEXT NOT NULL, "+NAME__HOSPITAL + " TEXT NOT NULL, " + PLACE__HOSPITAL + " TEXT, " + NUMBER__HOSPITAL + " TEXT, "+IMAGE_PHARMA_URL+" TEXT );";
    private static final String CREATE_TABLE_LABORATOIR = "create table " + TABLE_NAME_LABORATOIR + "(" + _ID_LABORATOIR
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + _ID_LABORATOIR_FIREBASE+ " TEXT NOT NULL, "+NAME__LABORATOIR + " TEXT NOT NULL, " + PLACE__LABORATOIR + " TEXT, " + NUMBER__LABORATOIR+" TEXT, " +IMAGE_LABORATOIR_URL+" TEXT );";
    private static final String CREATE_TABLE_DOCTORS = "create table " + TABLE_NAME_DOCTORS + "(" + _ID_DOCTOR
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + _ID_DOCTOR_FIREBASE + " TEXT NOT NULL, " + NAME_DOCTOR + " TEXT NOT NULL, " + PLACE_DOCTOR + " TEXT, " + PHONE_DOCTOR+ " TEXT, "+SPEC_DOCTOR+" TEXT NOT NULL, "+SEX_DOCTOR+" TEXT, "+IMAGE_DOCTOR_URL + " TEXT );";
    private static final String CREATE_TABLE_MESSAGES ="create table "+TABLE_NAME_MESSAGES+"("+_ID_MESSAGE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + _ID_MESSAGE_SENDER_FIREBASE+ " TEXT NOT NULL, " +SENDER_MESSAGE_NAME+ " TEXT NOT NULL, " +RECENT_MESSAGE+ " TEXT, "+FULL_MESSAGE + " TEXT ,"+IMAGE_MESSAGE + " TEXT ,"+MESSAGE_RECENT_DATE+ " TEXT, "+IS_READ + " TEXT ,"+IMAGE_SENDER_MESSAGE_URL+" TEXT);";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DONATEUR);
        db.execSQL(CREATE_TABLE_PHARMACIE);
        db.execSQL(CREATE_TABLE_HOSPITAL);
        db.execSQL(CREATE_TABLE_DOCTORS);
        db.execSQL(CREATE_TABLE_LABORATOIR);
        db.execSQL(CREATE_TABLE_MESSAGES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DONATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PHARMACIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOSPITAL);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_LABORATOIR);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_MESSAGES);
        onCreate(db);
    }
}