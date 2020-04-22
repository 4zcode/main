package com.example.myapplication.toolsbar.sahti_fi_ydi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.FragmentRefreshListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private SharedPreferences myPef;
    private Button message_button;
    private Button profile_button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        message_button = root.findViewById(R.id.message_button_intent);
        profile_button = root.findViewById(R.id.profile_home_fragment_button);
        myPef = getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        ((MainActivity)getActivity()).setFragmentRefreshListener(new FragmentRefreshListener() {
            @Override
            public void onRefresh() {
           message_button.setText("Messages: "+myPef.getString("NbrMessageNoRead","Messages"));
            }
        });
        message_button.setText("Messages: "+myPef.getString("NbrMessageNoRead","Messages"));
        if (myPef.getBoolean("IsLogIn",false ) && FirebaseAuth.getInstance().getCurrentUser()!=null) {
            message_button.setVisibility(View.VISIBLE);
            profile_button.setVisibility(View.VISIBLE);

        }
        return root;
    }


}