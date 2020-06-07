package com.example.myapplication.doctors.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import org.apache.commons.lang3.text.WordUtils;

public class PersonalInformationDoctorFragment extends Fragment {


    //Les constants
    public static final String SPECIALISTE = "SPECIALISTÉ";
    public static final String SERVICES = "SERVICES";
    public static final String TIME = "TIME";

    //Les Views
   public ImageButton bService, bTime;
    public TextView tSpecialité,tService,tTime;

    public LinearLayout specLinear, serviceLinear, timeLinear, typeLinear;

    public PersonalInformationDoctorFragment(){
        //constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_information_doctor, container, false);


        bService=(ImageButton) view.findViewById(R.id.fragment_details_doctor_service_button);
        bTime=(ImageButton) view.findViewById(R.id.fragment_details_doctor_ouverture_button);
        tSpecialité=(TextView) view.findViewById(R.id.fragment_details_doctor_specialist);
        tService=(TextView) view.findViewById(R.id.fragment_details_doctor_service);
        tTime=(TextView) view.findViewById(R.id.fragment_details_doctor_overture);
        specLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_specialist_linearlayout);
        serviceLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_service_linearlayout);
        timeLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_ouverture_linearlayout);
        typeLinear=(LinearLayout) view.findViewById(R.id.fragment_details_doctor_type_linearlayout);



        specLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));
        serviceLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));
        timeLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));
        typeLinear.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.push_up_in));

        //initialiser les views
        initialeTheViewIntent();

        //performer les onClicks
        bService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tService.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tTime.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initialeTheViewIntent () {
        Intent i = getActivity().getIntent();
        tSpecialité.setText(WordUtils.capitalizeFully(i.getStringExtra(SPECIALISTE)));
        tService.setText(WordUtils.capitalizeFully(i.getStringExtra(SERVICES)));
        tTime.setText(WordUtils.capitalizeFully(i.getStringExtra(TIME)));
    }


}
