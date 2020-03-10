package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Pharmacies.pharmaciesinit;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "Pharmacies";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String PLACE = "place";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";

    // Database Information
    static final String DB_NAME = "Pharmacie.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + PLACE + " TEXT, " + OPEN + " TEXT, " + CLOSE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
   public ArrayList<pharmaciesinit> listPharmacies() {
        String sql = "select * from " + TABLE_NAME;
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
