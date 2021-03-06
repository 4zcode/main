package com.example.myapplication.doctors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.DatabaseHelper;

import java.util.ArrayList;


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


    public void insert(String _id,String name, String place,String phone, String spec,String sex,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_DOCTOR_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME_DOCTOR, name);
        contentValue.put(DatabaseHelper.PLACE_DOCTOR, place);
        contentValue.put(DatabaseHelper.SEX_DOCTOR, sex);
        contentValue.put(DatabaseHelper.SPEC_DOCTOR, spec);
        contentValue.put(DatabaseHelper.PHONE_DOCTOR, phone);
        contentValue.put(DatabaseHelper.IMAGE_DOCTOR_URL, imageUrl);

        database.insert(DatabaseHelper.TABLE_NAME_DOCTORS, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_DOCTOR, DatabaseHelper.NAME_DOCTOR, DatabaseHelper.PLACE_DOCTOR, DatabaseHelper.PHONE_DOCTOR, DatabaseHelper.SPEC_DOCTOR, DatabaseHelper.SEX_DOCTOR, DatabaseHelper.IMAGE_DOCTOR_URL};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_DOCTORS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _id, String name, String place,String phone, String spec,String sex,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_DOCTOR, name);
        contentValues.put(DatabaseHelper.PLACE_DOCTOR, place);
        contentValues.put(DatabaseHelper.SEX_DOCTOR, sex);
        contentValues.put(DatabaseHelper.SPEC_DOCTOR, spec);
        contentValues.put(DatabaseHelper.PHONE_DOCTOR, phone);
        contentValues.put(DatabaseHelper.IMAGE_DOCTOR_URL, imageUrl);

        int i = database.update(DatabaseHelper.TABLE_NAME_DOCTORS, contentValues, DatabaseHelper._ID_DOCTOR_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME_DOCTORS, DatabaseHelper._ID_DOCTOR + "=" + _id, null);
    }
    public int deleteAll(){
        return database.delete(DatabaseHelper.TABLE_NAME_DOCTORS,"1",null);
    }


    public ArrayList<Doctors> listdoctors(int k,String name, String adresse, String speciality) {
        String sql = "select * from " + DatabaseHelper.TABLE_NAME_DOCTORS;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<Doctors> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        Boolean all = false;
        if (adresse.toLowerCase().contains("wilaya")) all = true;
        boolean nameVide = false;
        if (name.isEmpty()) nameVide = true;

        if (cursor.moveToFirst()) {
            do {
                String place = cursor.getString(3);
                String spec = cursor.getString(5);
                String Name = cursor.getString(2);

                if ((nameVide ||Name.toLowerCase().contains(name)) &&(all || place.toLowerCase().contains(adresse.toLowerCase()) )&& spec.toLowerCase().contains(speciality.toLowerCase())) {
                    int id = Integer.parseInt(cursor.getString(0));
                    String Id_firebase = cursor.getString(1);
                    String phone = cursor.getString(4);
                    String sex = cursor.getString(6);
                    String imageUrl = cursor.getString(7);

                    storeContacts.add(new Doctors(Id_firebase, Name, place, phone, spec, sex, imageUrl));
                }
            }
            while (cursor.moveToNext() && storeContacts.size() < (25 + 5*k));
        }
        cursor.close();
        return storeContacts;
    }
}
