package com.example.myapplication.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.myapplication.message.chatRoom;

import java.util.ArrayList;
import java.util.List;

public class tools {
    public static final int LOCATION_PERMISSION = 99;


    public static boolean isNetworkAvailable(Context context) {
        boolean HaveConnectWIFI = false;
        boolean HaveConnectMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo ni : activeNetworkInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    HaveConnectWIFI = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    HaveConnectMobile = true;
        }
        return HaveConnectMobile || HaveConnectWIFI;
    }

    public static Spanned getSpannedText(String text){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            return Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT);
        }else{ return Html.fromHtml(text);}
    }


    public static void hideKeyBoard(Activity activity,View view){
        InputMethodManager imm = (InputMethodManager) activity.getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view1 =activity.getCurrentFocus();
        if(view1 == null){
            view=new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public static void checkPermissions( Activity activity, LocationManager locationManager,LocationListener locationListener){


        int permissionLocation = ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermission = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermission.isEmpty()){
                ActivityCompat.requestPermissions(activity,listPermission.toArray(new String[listPermission.size()]),LOCATION_PERMISSION);
            }
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }



}
