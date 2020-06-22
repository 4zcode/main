package com.example.myapplication.toolsbar.don_de_sang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME_DONATEUR;

public class DBManagerDonateur {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerDonateur(Context c) {
        context = c;
    }

    public DBManagerDonateur open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_DONATEUR + " where " + DatabaseHelper._ID_DONATEUR_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void insert(String _id,String name, String place,String wilaya, String commune, String phone,String age,String grsanguin,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_DONATEUR_FIREBASE, _id);
        contentValue.put(DatabaseHelper.AGE, age);
        contentValue.put(DatabaseHelper.NAME_DONATEUR, name);
        contentValue.put(DatabaseHelper.PLACE_DONATEUR, place);
        contentValue.put(DatabaseHelper.WILAYA, wilaya);
        contentValue.put(DatabaseHelper.COMMUNE, commune);
        contentValue.put(DatabaseHelper.PHONE_DONATEUR, phone);
        contentValue.put(DatabaseHelper.GRSANGUIN_DONATEUR, grsanguin);
        contentValue.put(DatabaseHelper.IMAGE_DONATEUR_URL, imageUrl);
        database.insert(TABLE_NAME_DONATEUR, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME_DONATEUR, DatabaseHelper.PLACE_DONATEUR,DatabaseHelper.AGE,DatabaseHelper.PHONE_DONATEUR,DatabaseHelper.GRSANGUIN_DONATEUR,DatabaseHelper.IMAGE_DONATEUR_URL};
        Cursor cursor = database.query(TABLE_NAME_DONATEUR, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _id, String name, String place,String wilaya, String commune, String phone,String age,String grsnaguin,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_DONATEUR, name);
        contentValues.put(DatabaseHelper.AGE, age);
        contentValues.put(DatabaseHelper.PLACE_DONATEUR, place);
        contentValues.put(DatabaseHelper.WILAYA, wilaya);
        contentValues.put(DatabaseHelper.COMMUNE, commune);
        contentValues.put(DatabaseHelper.PHONE_DONATEUR, phone);
        contentValues.put(DatabaseHelper.GRSANGUIN_DONATEUR, grsnaguin);
        contentValues.put(DatabaseHelper.IMAGE_DONATEUR_URL, imageUrl);

        int i = database.update(TABLE_NAME_DONATEUR, contentValues, DatabaseHelper._ID_DONATEUR_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(TABLE_NAME_DONATEUR, DatabaseHelper._ID+ "=" + _id, null);
    }
    public ArrayList<don_de_song> listdonateur() {
        String sql = "select * from " + TABLE_NAME_DONATEUR;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<don_de_song> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String name = cursor.getString(2);
                String place = cursor.getString(3);
                String wilaya = cursor.getString(4);
                String commune = cursor.getString(5);
                String phone = cursor.getString(6);
                String age=cursor.getString(7);
                String grsnaguin=cursor.getString(8);
                String imageUrl = cursor.getString(9);

                storeContacts.add(new don_de_song(Id_firebase,name, place,wilaya,commune, phone,age,grsnaguin,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
    public void deleteall(){
        String sql = "select * from " + TABLE_NAME_DONATEUR;
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
