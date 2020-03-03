package com.example.myapplication.doctors;

public class DoctorsSpecialistes {
    private final String SpecialisteName;
    private final int SpecialisteImage;




    DoctorsSpecialistes(String name, int image) {
        this.SpecialisteName = name;
        this.SpecialisteImage = image;
    }


        String getSpecialiste() {
        return this.SpecialisteName;
    }

      int getImageSpecialiste() {return this.SpecialisteImage;}
}
