package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Pharmacies.pharmaciesinit;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String place,String open, String close) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.PLACE, place);
        contentValue.put(DatabaseHelper.OPEN, open);
        contentValue.put(DatabaseHelper.CLOSE, close);
        database.insert(TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PLACE, DatabaseHelper.OPEN, DatabaseHelper.CLOSE };
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String place,String open, String close) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.PLACE, place);
        contentValues.put(DatabaseHelper.OPEN, open);
        contentValues.put(DatabaseHelper.CLOSE, close);
        int i = database.update(TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
    public ArrayList<pharmaciesinit> listPharmacies() {
        String sql = "select * from " + TABLE_NAME;
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
