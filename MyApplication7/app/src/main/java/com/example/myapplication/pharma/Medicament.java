package com.example.myapplication.pharma;

public class Medicament {
    private final String MedicamenName;
    private final String MedicamenPrix;
    private final String MedicamentClass;
    private final String Id;
    private final String num_eng;
    private final String code;
    private final String domination_c_in;
    private final String dosage;
    private final String cond;
    private final String liste;
    private final String pays_du_lab;
    private final String date_deng_ini;
    private final String date_deng_final;
    private final String forme;
    private final String statut;
    private final String duree_de_stab;
    private final String remboursement;

    public Medicament(String medicamenName, String medicamenPrix, String medicamentClass, String id, String num_eng, String code, String domination_c_in, String dosage, String cond, String liste, String pays_du_lab, String date_deng_ini, String date_deng_final, String forme, String statut, String duree_de_stab, String remboursement) {
        MedicamenName = medicamenName;
        MedicamenPrix = medicamenPrix;
        MedicamentClass = medicamentClass;
        this.Id = id;
        this.num_eng = num_eng;
        this.code = code;
        this.domination_c_in = domination_c_in;
        this.dosage = dosage;
        this.cond = cond;
        this.liste = liste;
        this.pays_du_lab = pays_du_lab;
        this.date_deng_ini = date_deng_ini;
        this.date_deng_final = date_deng_final;
        this.forme = forme;
        this.statut = statut;
        this.duree_de_stab = duree_de_stab;
        this.remboursement = remboursement;
    }



   /* Medicament(String name, String prix, String mClass) {
        this.MedicamenName = name;
        this.MedicamenPrix = prix;
        this.MedicamentClass = mClass;
    }


    */

    public String getMedicamenName() {
        return MedicamenName;
    }

   public  String getMedicamenPrix() {
        return MedicamentClass;
    }

   public  String getMedicamentClass() {
        return MedicamenPrix;
    }
    public String getId() {
        return Id;
    }

    public String getNum_eng() {
        return num_eng;
    }

    public String getCode() {
        return code;
    }

    public String getDomination_c_in() {
        return domination_c_in;
    }

    public String getDosage() {
        return dosage;
    }

    public String getCond() {
        return cond;
    }

    public String getListe() {
        return liste;
    }

    public String getPays_du_lab() {
        return pays_du_lab;
    }

    public String getDate_deng_ini() {
        return date_deng_ini;
    }

    public String getDate_deng_final() {
        return date_deng_final;
    }

    public String getForme() {
        return forme;
    }

    public String getStatut() {
        return statut;
    }

    public String getDuree_de_stab() {
        return duree_de_stab;
    }

    public String getRemboursement() {
        return remboursement;
    }


}
