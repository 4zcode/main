package com.example.myapplication.Pharmacies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.ArrayList;

public class phbased extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String pharmaciestable = "pharmaciestable";
    private static final String DATABASENAME = "sahtifiyidi";
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    // Column names...
    public static final String KEYID = "id";
    public static final String FULLNAME = "fullname";
    public static final String ADRESS = "adress";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";

    // ... and a string array of columns.
    private static final String[] COLUMNS = {KEYID, FULLNAME, ADRESS, OPEN, CLOSE};
    private static final String PHARMACIESTABLECREATE ="CREATE TABLE "+pharmaciestable+"("+KEYID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FULLNAME+" TEXT, "+ADRESS+" TEXT, "+OPEN+" TEXT, "+CLOSE+" TEXT);";

    public phbased(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pharmaciestable(id INTEGER PRIMARY KEY AUTOINCREMENT,fullname TEXT,adress TEXT,open TEXT,close TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS "+pharmaciestable);
           onCreate(db);

    }
    public  ArrayList<pharmaciesinit> pharlist(){
           String sql="select * from "+pharmaciestable;
           SQLiteDatabase dp=this.getReadableDatabase();
           ArrayList<pharmaciesinit> list=new ArrayList<pharmaciesinit>();
           Cursor cursor=dp.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(1);
                String adress=cursor.getString(2);
                String open=cursor.getString(3);
                String close=cursor.getString(4);
                list.add(new pharmaciesinit(name,adress,open,close));
            }while(cursor.moveToNext());}
           cursor.close();
        return list;}
        public void insert(String name,String adress,String open,String close){
        ContentValues val=new ContentValues();
        val.put("name",name);
            val.put("adress",adress);
            val.put("open",open);
            val.put("close",close);
            mReadableDB=this.getWritableDatabase();
            mReadableDB.insert(pharmaciestable,null,val);
        }

}
