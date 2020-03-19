package com.example.myapplication.doctors;

public class Doctors {
    private String Doctor_ID_Firebase;
    private final String NameDoctor;
    private final String PlaceDoctor;
    private final String phone;
    private final String spec;
    private final String SexDoctor;



    public  Doctors(String id,String name, String place, String phone, String spec, String sex) {
       this.Doctor_ID_Firebase=id;
        this.NameDoctor = name;
        this.PlaceDoctor = place;
        this.phone = phone;
        this.spec = spec;
        this.SexDoctor = sex;
    }


    public String getNameDoctor() {
        return NameDoctor;
    }

    public String getPlaceDoctor() {
        return PlaceDoctor;
    }
    public String getSexDoctor(){
        return SexDoctor;
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
}
