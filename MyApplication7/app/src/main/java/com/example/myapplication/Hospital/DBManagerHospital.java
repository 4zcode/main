package com.example.myapplication.Hospital;

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

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_HOSPITAL + " where " + DatabaseHelper._ID_HOSPITAL_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public Hopital getHopitalFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_HOSPITAL + " where " + DatabaseHelper._ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String description = cursor.getString(3);
            String place = cursor.getString(4);
            int type = cursor.getInt(5);
            String phone = cursor.getString(6);
            String service = cursor.getString(7);
            String imageUrl = cursor.getString(8);
            cursor.close();
            return new Hopital(Id_firebase,name, description,place,type ,phone,service,imageUrl);
        }
        cursor.close();
        return null;
    }

    public void insert(String _id,String name,String description, String place,int type,String phone,String service,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_HOSPITAL_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValue.put(DatabaseHelper.DESCRIPTION__HOSPITAL, description);
        contentValue.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValue.put(DatabaseHelper.TYPE__HOSPITAL, type);
        contentValue.put(DatabaseHelper.NUMBER__HOSPITAL, phone);
        contentValue.put(DatabaseHelper.SERVICE__HOSPITAL, service);
        contentValue.put(DatabaseHelper.IMAGE_HOSPITAL_URL, imageUrl);
        database.insert(TABLE_NAME_HOSPITAL, null, contentValue);
    }

    public int update(String _id, String name,String description ,String place,int type,String phone,String service,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValues.put(DatabaseHelper.DESCRIPTION__HOSPITAL, description);
        contentValues.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValues.put(DatabaseHelper.TYPE__HOSPITAL, type);
        contentValues.put(DatabaseHelper.NUMBER__HOSPITAL, phone);
        contentValues.put(DatabaseHelper.SERVICE__HOSPITAL, service);
        contentValues.put(DatabaseHelper.IMAGE_HOSPITAL_URL, imageUrl);

        int i = database.update(TABLE_NAME_HOSPITAL, contentValues, DatabaseHelper._ID_HOSPITAL_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }


    public void delete(long _id) {
        database.delete(TABLE_NAME_HOSPITAL, DatabaseHelper._ID+ "=" + _id, null);
    }

    public int deleteAll(){
        return database.delete(TABLE_NAME_HOSPITAL,"1",null);
    }


}