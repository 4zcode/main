package com.example.myapplication.message;

import android.content.Context;
import android.content.Intent;

import java.util.Comparator;
import java.util.Date;

public class Message implements Comparable<Message>{
    private String Message_ID_Firebase;
    private final String Sender;
    private final String message;
    private final String is_Readed;
    private final int imageResource;

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    private Date Date;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";



    public Message(String id,String senderName, String message, int hopitalImage,String readStatus, Date date) {
        this.Message_ID_Firebase=id;
        this.Sender = senderName;
        this.message = message;
        this.imageResource = hopitalImage;
        is_Readed = readStatus;
        this.Date =date;
    }


    public String getSender() {
        return Sender;
    }

    public String getMessage() {
        return message;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getMessage_ID_Firebase() {
        return Message_ID_Firebase;
    }
    public boolean Is_Readed(){
        return this.is_Readed.equals("true");
    }
    public void setMessage_ID_Firebase(String message_ID_Firebase) {
        Message_ID_Firebase = message_ID_Firebase;
    }
    static Intent starter(Context context, String reciver, String sender) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        return chatIntent;
    }

    @Override
    public int compareTo(Message o) {
        return o.getDate().compareTo(this.getDate());
    }
}
