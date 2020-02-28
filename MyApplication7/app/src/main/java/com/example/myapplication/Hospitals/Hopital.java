package com.example.myapplication.Hospitals;

public class Hopital {
    private final String hopitalName;
    private final String hopitalPlace;
    private final String hopitalContact;
    private final int imageResource;



    Hopital(String name, String place, String contact, int hopitalImage) {
        this.hopitalName = name;
        this.hopitalPlace = place;
        this.hopitalContact=contact;
        this.imageResource = hopitalImage;
    }


    String getHopitalName() {
        return hopitalName;
    }

    String getHopitalPlace() {
        return hopitalPlace;
    }
    String getHopitalContact(){
        return hopitalContact;
    }

    int getImageResource() {
        return imageResource;
    }

}
