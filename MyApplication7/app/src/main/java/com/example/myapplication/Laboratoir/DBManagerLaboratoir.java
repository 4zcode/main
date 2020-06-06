package com.example.myapplication.Laboratoir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.DatabaseHelper.TABLE_NAME_LABORATOIR;

public class DBManagerLaboratoir {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerLaboratoir(Context c) {
        context = c;
    }

    public DBManagerLaboratoir open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }



    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_LABORATOIR + " where " + DatabaseHelper._ID_LABORATOIR_FIREBASE + " = '" + nameFeild +"' ";
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
        contentValue.put(DatabaseHelper._ID_LABORATOIR_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME__LABORATOIR, name);
        contentValue.put(DatabaseHelper.PLACE__LABORATOIR, place);
        contentValue.put(DatabaseHelper.NUMBER__LABORATOIR, phone);
        contentValue.put(DatabaseHelper.IMAGE_LABORATOIR_URL, imageUrl);
        database.insert(TABLE_NAME_LABORATOIR, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_LABORATOIR, DatabaseHelper.NAME__LABORATOIR, DatabaseHelper.PLACE__LABORATOIR, DatabaseHelper.NUMBER__LABORATOIR,DatabaseHelper.IMAGE_LABORATOIR_URL};
        Cursor cursor = database.query(TABLE_NAME_LABORATOIR, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _id, String name, String place,String phone,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME__LABORATOIR, name);
        contentValues.put(DatabaseHelper.PLACE__LABORATOIR, place);
        contentValues.put(DatabaseHelper.NUMBER__LABORATOIR, phone);
        contentValues.put(DatabaseHelper.IMAGE_LABORATOIR_URL, imageUrl);

        int i = database.update(TABLE_NAME_LABORATOIR, contentValues, DatabaseHelper._ID_LABORATOIR_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(TABLE_NAME_LABORATOIR, DatabaseHelper._ID_LABORATOIR + "=" + _id, null);
    }
    public int deleteAll(){
        return database.delete(TABLE_NAME_LABORATOIR,"1",null);
    }

    public ArrayList<Labo> listLabo() {
        String sql = "select * from " + TABLE_NAME_LABORATOIR;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<Labo> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String name = cursor.getString(2);
                String place = cursor.getString(3);
                String phone = cursor.getString(4);
                String imageUrl = cursor.getString(5);

                storeContacts.add(new Labo(Id_firebase,name, place, phone,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
    public void deleteall(){
        String sql = "select * from " + TABLE_NAME_LABORATOIR;
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
