package com.example.myapplication.doctors.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import org.apache.commons.lang3.text.WordUtils;

public class AddresseDoctorFragment extends Fragment {
    public static final String ADRESS = "ADRESS";
    public ImageButton bAdrres;
    public TextView tText;

    public AddresseDoctorFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addresse_doctor, container, false);
        bAdrres= (ImageButton) view.findViewById(R.id.fragment_adress_doctor_adrress_image_button);
       tText= (TextView) view.findViewById(R.id.fragment_adress_doctor_adrress_text);



        //initialiser les views
        initialeTheViewIntent();
        //onClick
        bAdrres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), tText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void initialeTheViewIntent () {
        Intent i = getActivity().getIntent();
        tText.setText(WordUtils.capitalizeFully(i.getStringExtra(ADRESS)));

    }
}
