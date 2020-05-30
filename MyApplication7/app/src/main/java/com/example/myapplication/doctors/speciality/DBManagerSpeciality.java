package com.example.myapplication.doctors.speciality;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.DatabaseHelper;

import java.util.ArrayList;


public class DBManagerSpeciality {
     private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerSpeciality(Context c) {
        context = c;
    }

    public DBManagerSpeciality open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + DatabaseHelper.TABLE_NAME_SPECIALITY + " where " + DatabaseHelper.SPECIALITY_NAME + " = '" + nameFeild +"' ";
            Cursor cursor = db.rawQuery(Query, null);
            if(cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
        return false;
    }
    public String getNumberSpec(String nameFeild) {
        String number ="0";
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + DatabaseHelper.TABLE_NAME_SPECIALITY + " where " + DatabaseHelper.SPECIALITY_NAME + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            number= cursor.getString(2);
        }
        cursor.close();
        return number;
    }


    public void insert(long _id,String name, String count) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_SPECIALITY, _id);
        contentValue.put(DatabaseHelper.SPECIALITY_NAME, name);
        contentValue.put(DatabaseHelper.SPECIALITY_NUMBER, count);

        database.insert(DatabaseHelper.TABLE_NAME_SPECIALITY, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_SPECIALITY, DatabaseHelper.SPECIALITY_NAME, DatabaseHelper.SPECIALITY_NUMBER};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_SPECIALITY, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id,String name, String count) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SPECIALITY_NAME, name);
        contentValues.put(DatabaseHelper.SPECIALITY_NUMBER, count);;

        int i = database.update(DatabaseHelper.TABLE_NAME_SPECIALITY, contentValues, DatabaseHelper._ID_SPECIALITY + " = " +_id, null);
        return i;
    }
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME_SPECIALITY, DatabaseHelper._ID_SPECIALITY + "=" + _id, null);
    }
    public int deleteAll(){
        return database.delete(DatabaseHelper.TABLE_NAME_SPECIALITY,"1",null);
    }


    public ArrayList<Speciality> listspeciality() {
        String sql = "select * from " + DatabaseHelper.TABLE_NAME_SPECIALITY;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<Speciality> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                long id = Integer.parseInt(cursor.getString(0));
                String Name = cursor.getString(1);
                String count = cursor.getString(2);
                storeContacts.add(new Speciality(id, Name, count));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
}
