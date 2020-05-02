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

    public void insert(String _id,String name, String place,String phone, String open,String close,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_PHARMA_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME_PHARMA, name);
        contentValue.put(DatabaseHelper.PLACE_PHARMA, place);
        contentValue.put(DatabaseHelper.PHONE_PHARMA, phone);
        contentValue.put(DatabaseHelper.OPEN_PHARMA, open);
        contentValue.put(DatabaseHelper.CLOSE_PHARMA, close);
        contentValue.put(DatabaseHelper.IMAGE_PHARMA_URL, imageUrl);

        database.insert(TABLE_NAME_PHARMACIE, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_PHARMA, DatabaseHelper.NAME_PHARMA, DatabaseHelper.PLACE_PHARMA, DatabaseHelper.PHONE_PHARMA, DatabaseHelper.OPEN_PHARMA,DatabaseHelper.CLOSE_PHARMA,DatabaseHelper.IMAGE_PHARMA_URL};
        Cursor cursor = database.query(TABLE_NAME_PHARMACIE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _id, String name, String place,String phone,String open,String close,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME_PHARMA, name);
        contentValues.put(DatabaseHelper.PLACE_PHARMA, place);
        contentValues.put(DatabaseHelper.PHONE_PHARMA, phone);
        contentValues.put(DatabaseHelper.OPEN_PHARMA, open);
        contentValues.put(DatabaseHelper.CLOSE_PHARMA, close);
        contentValues.put(DatabaseHelper.IMAGE_PHARMA_URL, imageUrl);

        int i = database.update(TABLE_NAME_PHARMACIE, contentValues, DatabaseHelper._ID_PHARMA_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(TABLE_NAME_PHARMACIE, DatabaseHelper._ID_PHARMA + "=" + _id, null);
    }
    public ArrayList<pharmacy> listPharmacies() {
        String sql = "select * from " + TABLE_NAME_PHARMACIE;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<pharmacy> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String name = cursor.getString(2);
                String place = cursor.getString(3);
                String phone = cursor.getString(4);
                String open = cursor.getString(5);
                String close = cursor.getString(6);
                String imageUrl = cursor.getString(7);

                storeContacts.add(new pharmacy(Id_firebase,name, place, phone,open,close,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
    public void deleteall(){
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
