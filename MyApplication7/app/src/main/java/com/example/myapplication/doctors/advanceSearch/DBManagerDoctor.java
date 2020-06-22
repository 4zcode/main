package com.example.myapplication.doctors.advanceSearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;


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

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + DatabaseHelper.TABLE_NAME_DOCTORS + " where " + DatabaseHelper._ID_DOCTOR_FIREBASE + " = '" + nameFeild +"' ";
            Cursor cursor = db.rawQuery(Query, null);
            if(cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
        return false;
    }

    public Doctors getDoctorFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + DatabaseHelper.TABLE_NAME_DOCTORS + " where " + DatabaseHelper._ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {

            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String place = cursor.getString(3);
            String wilaya =  cursor.getString(4);
            String commune = cursor.getString(5);
            String phone = cursor.getString(6);
            String specialite = cursor.getString(7);
            String type = cursor.getString(8);
            String service = cursor.getString(9);
            String time = cursor.getString(10);
            String imageUrl = cursor.getString(11);

            cursor.close();
            return new Doctors(Id_firebase,name, place,wilaya,commune,phone,specialite ,type,service,time,imageUrl);
        }
        cursor.close();
        return null;
    }

// DOCTOR_TYPE +DOCTOR_SERVICE +DOCTOR_TIME

    public void insert(String _id,String name, String place,String wilaya , String commune,String phone, String spec,String type,String service,String time,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_DOCTOR_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME_DOCTOR, name);
        contentValue.put(DatabaseHelper.PLACE_DOCTOR, place);
        contentValue.put(DatabaseHelper.WILAYA, wilaya);
        contentValue.put(DatabaseHelper.COMMUNE, commune);
        contentValue.put(DatabaseHelper.DOCTOR_TYPE, type);
        contentValue.put(DatabaseHelper.DOCTOR_SERVICE, service);
        contentValue.put(DatabaseHelper.DOCTOR_TIME, time);
        contentValue.put(DatabaseHelper.SPEC_DOCTOR, spec);
        contentValue.put(DatabaseHelper.PHONE_DOCTOR, phone);
        contentValue.put(DatabaseHelper.IMAGE_DOCTOR_URL, imageUrl);

        database.insert(DatabaseHelper.TABLE_NAME_DOCTORS, null, contentValue);
    }

    public int update(String _id,String name, String place, String wilaya, String commune,String phone, String spec,String type,String service,String time,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_DOCTOR, name);
        contentValues.put(DatabaseHelper.PLACE_DOCTOR, place);
        contentValues.put(DatabaseHelper.WILAYA, wilaya);
        contentValues.put(DatabaseHelper.COMMUNE, commune);
        contentValues.put(DatabaseHelper.DOCTOR_TYPE, type);
        contentValues.put(DatabaseHelper.DOCTOR_SERVICE, service);
        contentValues.put(DatabaseHelper.DOCTOR_TIME, time);
        contentValues.put(DatabaseHelper.SPEC_DOCTOR, spec);
        contentValues.put(DatabaseHelper.PHONE_DOCTOR, phone);
        contentValues.put(DatabaseHelper.IMAGE_DOCTOR_URL, imageUrl);

        int i = database.update(DatabaseHelper.TABLE_NAME_DOCTORS, contentValues, DatabaseHelper._ID_DOCTOR_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME_DOCTORS, DatabaseHelper._ID + "=" + _id, null);
    }

    public void deleteByFireBaseId(String _id) {
        database.delete(DatabaseHelper.TABLE_NAME_DOCTORS, DatabaseHelper._ID_DOCTOR_FIREBASE + " = '" + _id +"'", null);
    }


    public int deleteAll(){
        return database.delete(DatabaseHelper.TABLE_NAME_DOCTORS,"1",null);
    }

}
