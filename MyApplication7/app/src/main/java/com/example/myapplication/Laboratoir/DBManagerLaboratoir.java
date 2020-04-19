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
    private static DatabaseHelper dbHelper;
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
    public static void close() {
        dbHelper.close();
    }

    public void insert(String name, String place,String number) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME__LABORATOIR, name);
        contentValue.put(DatabaseHelper.PLACE__LABORATOIR, place);
        contentValue.put(DatabaseHelper.NUMBER__LABORATOIR, number);
        database.insert(TABLE_NAME_LABORATOIR, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_LABORATOIR, DatabaseHelper.NAME__LABORATOIR, DatabaseHelper.PLACE__LABORATOIR, DatabaseHelper.NUMBER__LABORATOIR};
        Cursor cursor = database.query(TABLE_NAME_LABORATOIR, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String place,String number) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME__LABORATOIR, name);
        contentValues.put(DatabaseHelper.PLACE__LABORATOIR, place);
        contentValues.put(DatabaseHelper.NUMBER__LABORATOIR, number);
        int i = database.update(TABLE_NAME_LABORATOIR, contentValues, DatabaseHelper._ID_LABORATOIR + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME_LABORATOIR, DatabaseHelper._ID_LABORATOIR + "=" + _id, null);
    }
    public static ArrayList<Labo> listLabo() {
        String sql = "select * from " + TABLE_NAME_LABORATOIR;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Labo> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String place = cursor.getString(2);
                String number = cursor.getString(3);
                storeContacts.add(new Labo(name, place,number, R.drawable.hospital));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
}
