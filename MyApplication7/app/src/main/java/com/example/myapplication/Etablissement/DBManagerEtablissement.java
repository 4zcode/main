package com.example.myapplication.Etablissement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;

public class DBManagerEtablissement {
    private DatabaseHelper dbHelper;

    private Context context;
    private String mTableName;
    private SQLiteDatabase database;

    public DBManagerEtablissement(Context c) {
        context = c;
    }

    public DBManagerEtablissement open(String table) throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        this.mTableName = table;
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + this.mTableName + " where " + DatabaseHelper._ID_HOSPITAL_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public Etablissement getHopitalFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + this.mTableName + " where " + DatabaseHelper._ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String description = cursor.getString(3);
            String place = cursor.getString(4);
            String wilaya = cursor.getString(5);
            String commune =  cursor.getString(6);
            int type = cursor.getInt(7);
            String phone = cursor.getString(8);
            String service = cursor.getString(9);
            String imageUrl = cursor.getString(10);
            cursor.close();
            return new Etablissement(Id_firebase,name, description,place,wilaya,commune,type ,phone,service,imageUrl);
        }
        cursor.close();
        return null;
    }

    public void insert(String _id,String name,String description, String place,String wilaya , String commune,
                       int type,String phone,String service,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_HOSPITAL_FIREBASE, _id);
        contentValue.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValue.put(DatabaseHelper.DESCRIPTION__HOSPITAL, description);
        contentValue.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValue.put(DatabaseHelper.WILAYA, wilaya);
        contentValue.put(DatabaseHelper.COMMUNE, commune);
        contentValue.put(DatabaseHelper.TYPE__HOSPITAL, type);
        contentValue.put(DatabaseHelper.NUMBER__HOSPITAL, phone);
        contentValue.put(DatabaseHelper.SERVICE__HOSPITAL, service);
        contentValue.put(DatabaseHelper.IMAGE_HOSPITAL_URL, imageUrl);
        database.insert(this.mTableName, null, contentValue);
    }

    public int update(String _id, String name,String description ,String place,String wilaya , String commune,
                      int type,String phone,String service,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME__HOSPITAL, name);
        contentValues.put(DatabaseHelper.DESCRIPTION__HOSPITAL, description);
        contentValues.put(DatabaseHelper.PLACE__HOSPITAL, place);
        contentValues.put(DatabaseHelper.WILAYA, wilaya);
        contentValues.put(DatabaseHelper.COMMUNE, commune);
        contentValues.put(DatabaseHelper.TYPE__HOSPITAL, type);
        contentValues.put(DatabaseHelper.NUMBER__HOSPITAL, phone);
        contentValues.put(DatabaseHelper.SERVICE__HOSPITAL, service);
        contentValues.put(DatabaseHelper.IMAGE_HOSPITAL_URL, imageUrl);

        int i = database.update(this.mTableName, contentValues, DatabaseHelper._ID_HOSPITAL_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }


    public void delete(long _id) {
        database.delete(this.mTableName, DatabaseHelper._ID+ "=" + _id, null);
    }
    public void deleteByFireBaseId(String _id) {
        database.delete(this.mTableName, DatabaseHelper._ID_DOCTOR_FIREBASE + " = '" + _id +"'", null);
    }
    public int deleteAll(){
        return database.delete(this.mTableName,"1",null);
    }


}