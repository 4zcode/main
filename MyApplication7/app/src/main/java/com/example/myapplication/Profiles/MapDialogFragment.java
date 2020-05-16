package com.example.myapplication.Profiles;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapDialogFragment extends DialogFragment implements OnMapReadyCallback {
GoogleMap mMap;
    public MapDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root  = inflater.inflate(R.layout.fragment_map_dialog, container, false);
        SupportMapFragment mapFragment= (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng latLng = new LatLng(-31,77);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Ajouter votre établissement");
        mMap.addMarker(markerOptions);
    }
}
