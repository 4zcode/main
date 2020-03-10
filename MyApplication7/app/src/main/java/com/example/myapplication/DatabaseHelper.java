package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Hospitals.Hopital;
import com.example.myapplication.Pharmacies.pharmaciesinit;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME_PHARMACIE = "Pharmacies";
    public static final String TABLE_NAME_HOSPITAL = "hospital";

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

    // Database Information
    static final String DB_NAME = "SahtiFiYdi.db";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE_PHARMACIE = "create table " + TABLE_NAME_PHARMACIE + "(" + _ID_PHARMA
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_PHARMA + " TEXT NOT NULL, " + PLACE_PHARMA + " TEXT, " + OPEN + " TEXT, " + CLOSE + " TEXT);";
    private static final String CREATE_TABLE_HOSPITAL = "create table " + TABLE_NAME_HOSPITAL + "(" + _ID_HOSPITAL
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME__HOSPITAL + " TEXT NOT NULL, " + PLACE__HOSPITAL + " TEXT, " + NUMBER__HOSPITAL + " TEXT);";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PHARMACIE);
        db.execSQL(CREATE_TABLE_HOSPITAL);
    }
   public ArrayList<pharmaciesinit> listPharmacies() {
        String sql = "select * from " + TABLE_NAME_PHARMACIE;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<pharmaciesinit> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String place = cursor.getString(2);
                String open = cursor.getString(3);
                String close = cursor.getString(4);
                storeContacts.add(new pharmaciesinit(name, place,open,close));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PHARMACIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HOSPITAL);
        onCreate(db);
    }
}
