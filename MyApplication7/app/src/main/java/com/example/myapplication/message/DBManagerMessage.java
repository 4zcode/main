package com.example.myapplication.message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.message.messageBoit.MessageItem;

import java.util.ArrayList;


public class DBManagerMessage {

     private DatabaseHelper dbHelper;
     private Context context;
     private SQLiteDatabase database;


    public DBManagerMessage(Context c) {

        context = c;
    }

    public DBManagerMessage open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        dbHelper.close();
    }
    public String getFullMessage(String messageID){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + DatabaseHelper.TABLE_NAME_MESSAGES + " where " + DatabaseHelper._ID_MESSAGE_SENDER_FIREBASE + " = '" + messageID +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
           String msg = cursor.getString(4);
            cursor.close();
            return msg;
        }
        cursor.close();
        return "";
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + DatabaseHelper.TABLE_NAME_MESSAGES + " where " + DatabaseHelper._ID_MESSAGE_SENDER_FIREBASE + " = '" + nameFeild +"' ";
            Cursor cursor = db.rawQuery(Query, null);
            if(cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
        return false;
    }

    public int deleteAll(){
       return database.delete(DatabaseHelper.TABLE_NAME_MESSAGES,"1",null);
    }

    public void insert(String _id,String senderName, String recentMessage, String fullMessage,String dateMessage,String is_read,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper._ID_MESSAGE_SENDER_FIREBASE, _id);
        contentValue.put(DatabaseHelper.SENDER_MESSAGE_NAME, senderName);
        contentValue.put(DatabaseHelper.RECENT_MESSAGE, recentMessage);
        contentValue.put(DatabaseHelper.FULL_MESSAGE, fullMessage);
        contentValue.put(DatabaseHelper.MESSAGE_RECENT_DATE, dateMessage);
        contentValue.put(DatabaseHelper.IS_READ, is_read);
        contentValue.put(DatabaseHelper.IMAGE_SENDER_MESSAGE_URL, imageUrl);
        database.insert(DatabaseHelper.TABLE_NAME_MESSAGES, null, contentValue);
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID_MESSAGE, DatabaseHelper.SENDER_MESSAGE_NAME, DatabaseHelper.RECENT_MESSAGE, DatabaseHelper.FULL_MESSAGE, DatabaseHelper.MESSAGE_RECENT_DATE,DatabaseHelper.IS_READ, DatabaseHelper.IMAGE_DOCTOR_URL};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_MESSAGES, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String _id,String senderName, String recentMessage, String fullMessage,String dateMessage,String is_read,String imageUrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SENDER_MESSAGE_NAME, senderName);
        contentValues.put(DatabaseHelper.RECENT_MESSAGE, recentMessage);
        contentValues.put(DatabaseHelper.FULL_MESSAGE, fullMessage);
        contentValues.put(DatabaseHelper.MESSAGE_RECENT_DATE, dateMessage);
        contentValues.put(DatabaseHelper.IS_READ, fullMessage);
        contentValues.put(DatabaseHelper.IMAGE_SENDER_MESSAGE_URL, imageUrl);

        int i = database.update(DatabaseHelper.TABLE_NAME_MESSAGES, contentValues, DatabaseHelper._ID_MESSAGE_SENDER_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME_MESSAGES, DatabaseHelper._ID_MESSAGE + "=" + _id, null);
    }
    public void deleteF(String _id) {
        database.delete(DatabaseHelper.TABLE_NAME_MESSAGES, DatabaseHelper._ID_MESSAGE_SENDER_FIREBASE + "='" + _id+"'", null);
    }

    public ArrayList<MessageItem> listMessages() {
        String sql = "select * from " + DatabaseHelper.TABLE_NAME_MESSAGES;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<MessageItem> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String senderName = cursor.getString(2);
                String recentMessage = cursor.getString(3);
                String fullMessage = cursor.getString(4);
                String dateMessage = cursor.getString(5);
                String is_read = cursor.getString(6);
                String imageUrl = cursor.getString(7);

                storeContacts.add(new MessageItem(Id_firebase,senderName, recentMessage, fullMessage, dateMessage,is_read,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }
}
