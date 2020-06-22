package com.example.myapplication.toolsbar.sahti_fi_ydi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.FragmentRefreshListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private SharedPreferences myPef;
    private FloatingActionButton message_button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        message_button = root.findViewById(R.id.message_button_intent);
        myPef = getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        ((MainActivity)getActivity()).setFragmentRefreshListener(new FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                String nbr =  myPef.getString("NbrMessageNoRead","0");
                String message = Integer.parseInt(nbr) > 1 ?  "Vous avez "+nbr + "Messages" : Integer.parseInt(nbr) == 1 ? "Vous avez un nouveau message" : "";
                if (!message.equals(""))
                Toast.makeText(getContext(), message,Toast.LENGTH_LONG ).show();
                if (!myPef.getBoolean("IsLogIn",false ) || FirebaseAuth.getInstance().getCurrentUser()==null) {
                    message_button.setVisibility(View.INVISIBLE);

                }else{
                    message_button.setVisibility(View.VISIBLE);
                }

            }
        });


        String nbr =  myPef.getString("NbrMessageNoRead","0");
        String message = Integer.parseInt(nbr) > 1 ?  "Vous avez "+nbr + "Messages" : Integer.parseInt(nbr) == 1 ? "Vous avez un nouveau message" : "";
        if (!message.equals(""))
            Toast.makeText(getContext(), message,Toast.LENGTH_LONG ).show();
        if (myPef.getBoolean("IsLogIn",false ) && FirebaseAuth.getInstance().getCurrentUser()!=null) {
            message_button.setVisibility(View.VISIBLE);
        }
        return root;
    }


}