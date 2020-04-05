package com.example.myapplication.location;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocation implements LocationListener {
    private SharedPreferences myPef;
    private Context context;


    public MyLocation(Context context){
        this.context = context;
    }
    @Override
    public void onLocationChanged(Location location) {
        myPef = context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPef.edit();
        String CityName = null;
        editor.putString("Latitude", String.valueOf(location.getLatitude()));
        editor.putString("Longitude", String.valueOf(location.getLongitude()));
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                CityName = addresses.get(0).getLocality();
                editor.putString("city",CityName);
                editor.apply();
                Log.d("Main_activity_test", " city : " + CityName);
                Log.d("Main_activity_test", " street 2: " +addresses.get(0).getAddressLine(0));


            }else {
                Log.d("Main_activity_test", " no address ");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}