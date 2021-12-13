package com.example.d035skymap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationController  implements LocationListener,Controller {
    public static final String NO_AUTO_LOCATE = "no_auto_locate";
    // Must match the key in the preferences file.
    private static final String FORCE_GPS = "force_gps";
    private static final int MINIMUM_DISTANCE_BEFORE_UPDATE_METRES = 2000;
    private static final int LOCATION_UPDATE_TIME_MILLISECONDS = 600000;
    private static final float MIN_DIST_TO_SHOW_TOAST_DEGS = 0.01f;
    private Context context;
    private LocationManager locationManager;

    public LocationController(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void start() {
        try {
            if (locationManager == null) {
                return;
            }

            locationManager.requestLocationUpdates(null, LOCATION_UPDATE_TIME_MILLISECONDS,
                    MINIMUM_DISTANCE_BEFORE_UPDATE_METRES,
                    this);
            Location location = locationManager.getLastKnownLocation(null);

        } catch (SecurityException securityException) {
        }

    }
    @Override
    public void stop() {
        if (locationManager == null) {
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Only need get the location once.
        locationManager.removeUpdates(this);
    }


    @Override
    public void onProviderDisabled(String provider) {
        // No action.
    }

    @Override
    public void onProviderEnabled(String provider) {
        // No action.
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // No action.
    }
}
