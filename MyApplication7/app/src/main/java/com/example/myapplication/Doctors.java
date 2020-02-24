package com.example.myapplication;

public class Doctors {
    private final String NameDoctor;
    private final String PlaceDoctor;
    private final String SexDoctor;



    Doctors(String name, String place, String sex) {
        this.NameDoctor = name;
        this.PlaceDoctor = place;
        this.SexDoctor = sex;
    }


    String getNameDoctor() {
        return NameDoctor;
    }

    String getPlaceDoctor() {
        return PlaceDoctor;
    }
    String getSexDoctor(){
        return SexDoctor;
    }


}
