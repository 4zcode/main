package com.example.myapplication.message;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.chatRoom;

public class Message {
    private String Message_ID_Firebase;
    private final String Sender;
    private final String message;
    private final int imageResource;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";



    public Message(String id,String senderName, String message, int hopitalImage) {
        this.Message_ID_Firebase=id;
        this.Sender = senderName;
        this.message = message;
        this.imageResource = hopitalImage;
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

    public void setMessage_ID_Firebase(String message_ID_Firebase) {
        Message_ID_Firebase = message_ID_Firebase;
    }
    static Intent starter(Context context, String reciver, String sender) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        return chatIntent;
    }
}
