package com.example.myapplication.Pharmacies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME_PHARMACIE;

public class DBManagerPharmacy {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerPharmacy(Context c) {
        context = c;
    }

    public DBManagerPharmacy open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_PHARMACIE + " where " + DatabaseHelper._ID_PHARMA_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public pharmacy getPharmacieFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_PHARMACIE + " where " + DatabaseHelper._ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String place = cursor.getString(3);
            int wilaya = cursor.getInt(4);
            int commune = cursor.getInt(5);
            String phone = cursor.getString(6);
            String time = cursor.getString(7);
            String description = cursor.getString(8);
            String imageUrl = cursor.getString(9);
            cursor.close();
            return new pharmacy(Id_firebase,name, place, wilaya,commune,phone,time,imageUrl,description);
        }
        cursor.close();
        return null;
    }

    public void insert(String _id,String name, String place, int wilaya , int commune,String phone, String time,String imageUrl,String description) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_PHARMA_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME_PHARMA, name);
        contentValue.put(DatabaseHelper.PLACE_PHARMA, place);
        contentValue.put(DatabaseHelper.WILAYA, wilaya);
        contentValue.put(DatabaseHelper.COMMUNE, commune);
        contentValue.put(DatabaseHelper.PHONE_PHARMA, phone);
        contentValue.put(DatabaseHelper.TIME, time);
        contentValue.put(DatabaseHelper.IMAGE_PHARMA_URL, imageUrl);
        contentValue.put(DatabaseHelper.DESCRIPTION,description);
        database.insert(TABLE_NAME_PHARMACIE, null, contentValue);
    }


    public int update(String _id, String name, String place, int wilaya , int commune,String phone,String time,String imageUrl,String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_PHARMA, name);
        contentValues.put(DatabaseHelper.PLACE_PHARMA, place);
        contentValues.put(DatabaseHelper.WILAYA, wilaya);
        contentValues.put(DatabaseHelper.COMMUNE, commune);
        contentValues.put(DatabaseHelper.PHONE_PHARMA, phone);
        contentValues.put(DatabaseHelper.TIME, time);
        contentValues.put(DatabaseHelper.IMAGE_PHARMA_URL, imageUrl);
        contentValues.put(DatabaseHelper.DESCRIPTION,description);
        int i = database.update(TABLE_NAME_PHARMACIE, contentValues, DatabaseHelper._ID_PHARMA_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(TABLE_NAME_PHARMACIE, DatabaseHelper._ID + "=" + _id, null);
    }

    public void deleteByFireBaseId(String _id) {
        database.delete(TABLE_NAME_PHARMACIE, DatabaseHelper._ID_PHARMA_FIREBASE + " = " + "'"+_id+ "'", null);
    }

    public void deleteAll(){
        String sql = "select * from " + TABLE_NAME_PHARMACIE;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                this.delete(id);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

    }
}
