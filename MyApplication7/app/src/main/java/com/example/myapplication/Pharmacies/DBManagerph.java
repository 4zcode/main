package com.example.myapplication.Pharmacies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME_PHARMACIE;
import static com.example.myapplication.DatabaseHelper.TABLE_NAME_PHARMACIE;

public class DBManagerph {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerph(Context c) {
        context = c;
    }

    public DBManagerph open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String place,String open, String close) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME_PHARMA, name);
        contentValue.put(DatabaseHelper.PLACE_PHARMA, place);
        contentValue.put(DatabaseHelper.OPEN, open);
        contentValue.put(DatabaseHelper.CLOSE, close);
        database.insert(TABLE_NAME_PHARMACIE, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_PHARMA, DatabaseHelper.NAME_PHARMA, DatabaseHelper.PLACE_PHARMA, DatabaseHelper.OPEN, DatabaseHelper.CLOSE };
        Cursor cursor = database.query(TABLE_NAME_PHARMACIE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String place,String open, String close) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_PHARMA, name);
        contentValues.put(DatabaseHelper.PLACE_PHARMA, place);
        contentValues.put(DatabaseHelper.OPEN, open);
        contentValues.put(DatabaseHelper.CLOSE, close);
        int i = database.update(TABLE_NAME_PHARMACIE, contentValues, DatabaseHelper._ID_PHARMA + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME_PHARMACIE, DatabaseHelper._ID_PHARMA + "=" + _id, null);
    }
    public ArrayList<pharmaciesinit> listPharmacies() {
        String sql = "select * from " + TABLE_NAME_PHARMACIE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
}
