package com.example.myapplication.message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageChatItem implements Comparable<MessageChatItem>{




    private String Message_ID_Firebase;
    private final String msgName;
    private final String message;
    private final String imageResource;
    private Date Date;





    public MessageChatItem(String id, String senderName, String message, String date, String msgImage) {

        this.Message_ID_Firebase=id;
        this.msgName = senderName;
        this.message = message;
        this.imageResource = msgImage;

        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
        try {
            this.Date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String getMsgName() {
        return msgName;
    }

    public String getMessage() {
        return message;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getMessage_ID_Firebase() {
        return Message_ID_Firebase;
    }


    public void setMessage_ID_Firebase(String message_ID_Firebase) {
        Message_ID_Firebase = message_ID_Firebase;
    }

    public java.util.Date getDate() {

        return this.Date;
    }
    public String getDate2String() {
        DateFormat date = new SimpleDateFormat("d MMM yyyy, HH:mm:SS");
        return date.format(this.Date);
    }


    public void setDate(java.util.Date date) {
        Date = date;
    }

    public void setDate(String date) {

        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
        try {
            this.Date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(MessageChatItem o) {
        return this.getDate().compareTo(o.getDate());
    }


}
