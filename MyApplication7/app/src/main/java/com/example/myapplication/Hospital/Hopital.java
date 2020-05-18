package com.example.myapplication.Hospital;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.message.chatRoom;

public class Hopital {
    private String hospital_ID_Firebase;
     public String hopitalName;
     public String hopitalPlace;
     public String hopitalContact;
  public String imageResource;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";



    public Hopital( String hospital_ID_Firebase,String name, String place, String contact, String hopitalImage) {
        this.hopitalName = name;
        this.hopitalPlace = place;
        this.hopitalContact=contact;
        this.imageResource = hopitalImage;
        this.setHospital_ID_Firebase(hospital_ID_Firebase);
    }


    public String getHopitalName() {
        return hopitalName;
    }

   public String getHopitalPlace() {
        return hopitalPlace;
    }
   public String getHopitalContact(){
        return hopitalContact;
    }

  public  String getImageResource() {
        return imageResource;
    }
    static Intent starter(Context context, String reciver, String sender) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        return chatIntent;
    }

    public String getHospital_ID_Firebase() {
        return hospital_ID_Firebase;
    }

    public void setHospital_ID_Firebase(String hospital_ID_Firebase) {
        this.hospital_ID_Firebase = hospital_ID_Firebase;
    }
}
