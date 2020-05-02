package com.example.myapplication.pharma;

public class Medicament {
    private final String MedicamenName;
    private final String MedicamenPrix;
    private final String MedicamentClass;


    Medicament(String name, String prix, String mClass) {
        this.MedicamenName = name;
        this.MedicamenPrix = prix;
        this.MedicamentClass = mClass;
    }


    public String getMedicamenName() {
        return MedicamenName;
    }

   public  String getMedicamenPrix() {
        return MedicamentClass;
    }

   public  String getMedicamentClass() {
        return MedicamenPrix;
    }


}
