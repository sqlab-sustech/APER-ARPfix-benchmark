package com.example.d024mapboxevent;

import android.content.Context;
import android.content.Intent;

public class MapboxTelemetry {
    private Context context;

    public boolean optLocationIn() {
       /* if (checkLocationPermission()) {
            startLocation();
        }*/
        return true;
    }

    Intent obtainLocationServiceIntent() {
        Intent locationServiceIntent = new Intent(context, TelemetryService.class);
        return locationServiceIntent;
    }

    private void startLocation() {
        context.startService(obtainLocationServiceIntent());
        context.bindService(obtainLocationServiceIntent(), null, Context.BIND_AUTO_CREATE);
    }

    boolean checkLocationPermission() {
        if (PermissionsManager.areLocationPermissionsGranted(null)) {
            return true;
        } else {
//            permissionBackoff();
            PermissionsManager.requestLocationPermissions(null);
            return false;
        }
    }
}
