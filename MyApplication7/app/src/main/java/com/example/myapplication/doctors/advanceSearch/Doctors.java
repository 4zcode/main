package com.example.myapplication.doctors.advanceSearch;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.DetailsDoctorActivity;
import com.example.myapplication.doctors.details.Details_Doctor;
import com.example.myapplication.message.chatRoom.chatRoom;


public class Doctors {
    //Les constants
    public static final String FIREBASE_ID = "firebaseId";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String ADRESS = "ADRESS";
    public static final String SPECIALISTE = "SPECIALISTÉ";
    public static final String SERVICES = "SERVICES";
    public static final String TIME = "TIME";
    public static final String PHONE = "PHONE";




    private String Doctor_ID_Firebase;
    private final String NameDoctor;
    private final String PlaceDoctor;
    private final String phone;
    private final String spec;
    private final String type;
    private final String service;
    private final String time;
    private String ImageUrl;



    public  Doctors(String id,String name, String place, String phone, String spec, String type,String service,String time,String image) {
        this.Doctor_ID_Firebase=id;
        this.NameDoctor = name;
        this.PlaceDoctor = place;
        this.phone = phone;
        this.spec = spec;
        this.ImageUrl = image;
        this.type = type;
        this.service= service;
        this.time = time;
    }


    public String getNameDoctor() {
        return NameDoctor;
    }

    public String getPlaceDoctor() {
        return PlaceDoctor;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpec() {
        return spec;
    }


    public String getDoctor_ID_Firebase() {
        return Doctor_ID_Firebase;
    }

    public void setDoctor_ID_Firebase(String doctor_ID_Firebase) {
        Doctor_ID_Firebase = doctor_ID_Firebase;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    static Intent starter(Context context,String firebaseID,String name,String imageID,String adress,String service,String specialist,String phone,String time) {
        Intent chatIntent = new Intent(context, Details_Doctor.class);
        chatIntent.putExtra(FIREBASE_ID, firebaseID);
        chatIntent.putExtra(IMAGE,imageID );
        chatIntent.putExtra(NAME, name);
        chatIntent.putExtra(ADRESS, adress);
        chatIntent.putExtra(SERVICES, service);
        chatIntent.putExtra(SPECIALISTE, specialist);
        chatIntent.putExtra(TIME, time);
        chatIntent.putExtra(PHONE, phone);

        return chatIntent;
    }


    public String getType() {
        return type;
    }

    public String getService() {
        return service;
    }

    public String getTime() {
        return time;
    }
}
