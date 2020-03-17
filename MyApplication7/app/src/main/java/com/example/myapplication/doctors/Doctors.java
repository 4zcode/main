package com.example.myapplication.doctors;

public class Doctors {
    private final String NameDoctor;
    private final String PlaceDoctor;
    private final String phone;
    private final String spec;
    private final String SexDoctor;



    public  Doctors(String name, String place, String phone, String spec, String sex) {
        this.NameDoctor = name;
        this.PlaceDoctor = place;
        this.phone = phone;
        this.spec = spec;
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


    public String getPhone() {
        return phone;
    }

    public String getSpec() {
        return spec;
    }


}
