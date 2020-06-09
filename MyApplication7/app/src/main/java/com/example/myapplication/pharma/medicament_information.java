package com.example.myapplication.pharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;


public class medicament_information extends AppCompatActivity {
    TextView MedicamenName;
    TextView MedicamenPrix;
    TextView MedicamentClass;
    TextView num_eng;
    TextView code;
    TextView domination_c_in;
    TextView dosage;
    TextView cond;
    TextView liste;
    TextView pays_du_lab;
    TextView date_deng_ini;
    TextView date_deng_final;
    TextView forme;
    TextView statut;
    TextView duree_de_stab;
    TextView remboursement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_information);
        MedicamenName=(TextView) findViewById(R.id.nom_de_marque);
        MedicamenPrix=(TextView)findViewById(R.id.prix);
        MedicamentClass=(TextView)findViewById(R.id.type);
        num_eng=(TextView)findViewById(R.id.nun_eng);
        code=(TextView)findViewById(R.id.code);
        domination_c_in=(TextView)findViewById(R.id.domination_c_i);
        dosage=(TextView)findViewById(R.id.Dosage);
        cond=(TextView)findViewById(R.id.cond);
        liste=(TextView)findViewById(R.id.Liste);
        pays_du_lab=(TextView)findViewById(R.id.pays_du_laboratoir);
        date_deng_ini=(TextView)findViewById(R.id.date_deng_initail);
        date_deng_final=(TextView)findViewById(R.id.date_deng_final);
        forme=(TextView)findViewById(R.id.Forme);
        statut=(TextView)findViewById(R.id.statut);
        duree_de_stab=(TextView)findViewById(R.id.duree_de_stabilite);
        remboursement=(TextView)findViewById(R.id.remboursement);
        Intent intent=getIntent();
        MedicamenName.setText(intent.getStringExtra("name"));
        MedicamenPrix.setText( intent.getStringExtra("classe"));
        MedicamentClass.setText(intent.getStringExtra("prix"));
        num_eng.setText(intent.getStringExtra("num_eng"));
        code.setText(intent.getStringExtra("code"));
        domination_c_in.setText( intent.getStringExtra("dcin"));
        dosage.setText(intent.getStringExtra("Dosage"));
        cond.setText(intent.getStringExtra("cond"));
        liste.setText(intent.getStringExtra("liste"));
        pays_du_lab.setText( intent.getStringExtra("pays"));
        date_deng_ini.setText(intent.getStringExtra("date_ini"));
        date_deng_final.setText(intent.getStringExtra("date_final"));
        forme.setText(intent.getStringExtra("forme"));
        duree_de_stab.setText(intent.getStringExtra("duree_destsb"));
        statut.setText(intent.getStringExtra("statut"));
        remboursement.setText(intent.getStringExtra("remboursement"));

    }
}