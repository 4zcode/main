package com.example.myapplication.Hospitals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME_HOSPITAL;

public class DBManagerHospital {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DBManagerHospital(Context c) {
        context = c;
    }
    public DBManagerHospital open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String place,String number) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValue.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValue.put(DatabaseHelper.NUMBER__HOSPITAL, number);
        database.insert(TABLE_NAME_HOSPITAL, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_HOSPITAL, DatabaseHelper.NAME__HOSPITAL, DatabaseHelper.PLACE__HOSPITAL, DatabaseHelper.NUMBER__HOSPITAL};
        Cursor cursor = database.query(TABLE_NAME_HOSPITAL, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String place,String number) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValues.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValues.put(DatabaseHelper.NUMBER__HOSPITAL, number);
        int i = database.update(TABLE_NAME_HOSPITAL, contentValues, DatabaseHelper._ID_HOSPITAL + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME_HOSPITAL, DatabaseHelper._ID_HOSPITAL + "=" + _id, null);
    }
    public ArrayList<Hopital> listHopital() {
        String sql = "select * from " + TABLE_NAME_HOSPITAL;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Hopital> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String place = cursor.getString(2);
                String number = cursor.getString(3);
                storeContacts.add(new Hopital(name, place,number, R.drawable.hospital));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
}
