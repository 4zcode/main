package com.example.myapplication.Hospital;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.message.chatRoom;

public class Hopital {
    private String hospital_ID_Firebase;
    public String hopitalName;
    public String hopitalPlace;
    public String hopitalservice;
    public String hopitalContact;
    public String imageResource;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";



    public Hopital( String hospital_ID_Firebase,String name, String place, String contact,String service, String hopitalImage) {
        this.hopitalName = name;
        this.hopitalPlace = place;
        this.hopitalContact=contact;
        this.hopitalservice = service;
        this.imageResource = hopitalImage;
        this.setHospital_ID_Firebase(hospital_ID_Firebase);
    }




    public String getHopitalName() {
        return hopitalName;
    }
    public String getHopitalservice() {
        return hopitalservice;
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
    public static Intent starter1(Context context, String name,String serv, String loca, String phone) {
        Intent detailIntent = new Intent(context,HopitalActivity.class);
        detailIntent.putExtra("NAME_KEY",name);
        detailIntent.putExtra("SERV_KEY",serv);
        detailIntent.putExtra("LOCA_KEY",loca);
        detailIntent.putExtra("PHON_KEY",phone);
        return detailIntent;
    }

    public String getHospital_ID_Firebase() {
        return hospital_ID_Firebase;
    }

    public void setHospital_ID_Firebase(String hospital_ID_Firebase) {
        this.hospital_ID_Firebase = hospital_ID_Firebase;
    }

}
