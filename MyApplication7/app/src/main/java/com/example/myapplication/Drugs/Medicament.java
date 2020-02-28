package com.example.myapplication.Drugs;

public class Medicament {
    private final String MedicamenName;
    private final String MedicamenPrix;
    private final String MedicamentClass;


    Medicament(String name, String prix, String mClass) {
        this.MedicamenName = name;
        this.MedicamenPrix = prix;
        this.MedicamentClass = mClass;
    }


    String getMedicamenName() {
        return MedicamenName;
    }

    String getMedicamenPrix() {
        return MedicamentClass;
    }

    String getMedicamentClass() {
        return MedicamenPrix;
    }


}
