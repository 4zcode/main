package com.example.myapplication.Hospital;

public class Hopital {
    private final String hopitalName;
    private final String hopitalPlace;
    private final String hopitalContact;
    private final int imageResource;



    public Hopital(String name, String place, String contact, int hopitalImage) {
        this.hopitalName = name;
        this.hopitalPlace = place;
        this.hopitalContact=contact;
        this.imageResource = hopitalImage;
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

  public  int getImageResource() {
        return imageResource;
    }

}
