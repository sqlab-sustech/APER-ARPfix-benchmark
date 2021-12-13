package com.example.d033myplacedemo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class PlacesLocationManager {
    private Activity activity;
    private LocationManager locationManager;

    private boolean has_permission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            String permission = Manifest.permission.ACCESS_FINE_LOCATION;

            return (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setInterval(int seconds) {
        if (!has_permission()) {
            locationManager = null;
            request_permission();
            return;
        }

            // sanity checks
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                if (Build.VERSION.SDK_INT >= 28) {
                    if (!locationManager.isLocationEnabled()) return;
                }
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) return;
                long minTime = seconds * 1000;  // milliseconds
                float minDistance = 1.5f;            // 1.5 meters is about 5 ft
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        minTime,
                        minDistance,
                        (PendingIntent) null);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;

        // Make request on behalf of Activity.
        // The Activity will need to override "onRequestPermissionsResult",
        // and proxy the call to the same-name handler in this class.
        activity.requestPermissions(new String[]{permission}, 233);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 233: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

//                    setInterval(seconds);
                } else {
                    // permission denied
                }
            }
        }
    }

}
