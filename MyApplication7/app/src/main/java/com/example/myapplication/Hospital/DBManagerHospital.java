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

    public void insert(String _id,String name, String place,String phone,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_HOSPITAL_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValue.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValue.put(DatabaseHelper.NUMBER__HOSPITAL, phone);
        contentValue.put(DatabaseHelper.IMAGE_HOSPITAL_URL, imageUrl);
        database.insert(TABLE_NAME_HOSPITAL, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_HOSPITAL, DatabaseHelper.NAME__HOSPITAL, DatabaseHelper.PLACE__HOSPITAL, DatabaseHelper.NUMBER__HOSPITAL,DatabaseHelper.IMAGE_HOSPITAL_URL};
        Cursor cursor = database.query(TABLE_NAME_HOSPITAL, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _id, String name, String place,String phone,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValues.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValues.put(DatabaseHelper.NUMBER__HOSPITAL, phone);
        contentValues.put(DatabaseHelper.IMAGE_HOSPITAL_URL, imageUrl);

        int i = database.update(TABLE_NAME_HOSPITAL, contentValues, DatabaseHelper._ID_HOSPITAL_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }


    public void delete(long _id) {
        database.delete(TABLE_NAME_HOSPITAL, DatabaseHelper._ID_HOSPITAL+ "=" + _id, null);
    }

    public int deleteAll(){
        return database.delete(TABLE_NAME_HOSPITAL,"1",null);
    }


    public ArrayList<Hopital> listHospital() {
        String sql = "select * from " + TABLE_NAME_HOSPITAL;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<Hopital> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String name = cursor.getString(2);
                String place = cursor.getString(3);
                String phone = cursor.getString(4);
                String imageUrl = cursor.getString(5);

                storeContacts.add(new Hopital(Id_firebase,name, place, phone,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

}
