package com.mrede003.takehome.farmlogs.farmnotepad;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by mrede003 on 4/25/17.
 */
//Class that include static methods mainly related to permissions
//giving the ability to permission/gps status from any activity/context
public class Helper {

    public static boolean hasLocationPermission(Activity activity)
    {
         return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }
    public static boolean requestLocationPermission(Activity activity)
    {
        int result=0;
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                result);
        return hasLocationPermission(activity);
    }

    public static boolean checkGPSStatus(Context context)
    {
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
