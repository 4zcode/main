package com.example.myapplication.Laboratoir;

public class Labo {
    private final String laboName;
    private final String laboPlace;
    private final String laboContact;
    private final int imageResource;



    public Labo(String name, String place, String contact, int laboImage) {
        this.laboName = name;
        this.laboPlace = place;
        this.laboContact=contact;
        this.imageResource = laboImage;
    }


    public String getLaboName() {
        return laboName;
    }

   public String getLaboPlace() {
        return laboPlace;
    }
   public String getLaboContact(){
        return laboContact;
    }

  public  int getImageResource() {
        return imageResource;
    }

}
