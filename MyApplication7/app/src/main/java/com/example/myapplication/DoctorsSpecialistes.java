package com.example.myapplication;

public class DoctorsSpecialistes {
    private final String SpecialisteName;
   // private final int SpecialisteImage;




    DoctorsSpecialistes(String name) {
        this.SpecialisteName = name;
        //this.SpecialisteImage = image;
    }


        String getSpecialiste() {
        return this.SpecialisteName;
    }

   // int getImageSpecialiste() {return this.SpecialisteImage;}
}
