package com.example.myapplication.doctors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.Pharmacies.pharmaciesinit;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.DOCTOR_TABLE;


public class DBManagerDoctor {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerDoctor(Context c) {
        context = c;
    }

    public DBManagerDoctor open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String place,String phone) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DOCTOR_NAME, name);
        contentValue.put(DatabaseHelper.DOCTOR_PLACE, place);
        contentValue.put(DatabaseHelper.DOCTOR_SEX, phone);
        database.insert(DOCTOR_TABLE, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_Doctor, DatabaseHelper.DOCTOR_NAME, DatabaseHelper.DOCTOR_PLACE, DatabaseHelper.DOCTOR_SEX };
        Cursor cursor = database.query(DOCTOR_TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String place,String sex) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DOCTOR_NAME, name);
        contentValues.put(DatabaseHelper.DOCTOR_PLACE, place);
        contentValues.put(DatabaseHelper.DOCTOR_SEX, sex);
        int i = database.update(DOCTOR_TABLE, contentValues, DatabaseHelper._ID_Doctor + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DOCTOR_TABLE, DatabaseHelper._ID_Doctor + "=" + _id, null);
    }
    public ArrayList<Doctors> listDoctors() {
        String sql = "select * from " + DOCTOR_TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Doctors> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String place = cursor.getString(2);
                String sex = cursor.getString(3);
                storeContacts.add(new Doctors(name, place,sex));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
}
