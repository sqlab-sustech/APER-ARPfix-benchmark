package com.lucasg234.protesttracker.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.lucasg234.protesttracker.R;
import com.lucasg234.protesttracker.permissions.PermissionsHandler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Utility class which deals with Location, ParseGeoPoint, and LatLng objects
 */
public class LocationUtils {

    private static final String TAG = "LocationUtils";
    public static final double MILES_TO_FEET = 5280;

    // Returns the last known location of the user
    public static Location getCurrentLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!PermissionsHandler.checkLocationPermission(context)) {
            Log.e(TAG, "No location permissions");
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    // Returns a string displays the relative location of a post from the user's current location
    public static String toRelativeLocation(Context context) {

        Location currentLocation = getCurrentLocation(context);
        // Provide empty string because there is no provider
        Location targetLocation = new Location("");

        float metersBetween = currentLocation.distanceTo(targetLocation);

        return String.format(context.getString(R.string.distance_format), metersToImperialString(metersBetween));
    }

    // Helper method to convert meters to human readable imperial units
    private static String metersToImperialString(float meters) {
        float miles = meters * 0.000621371f;
        String out;
        if (miles < 0.1) {
            // If less than .1 miles use feet instead
            int feet = (int) Math.round(miles * MILES_TO_FEET);
            out = String.format(Locale.getDefault(), "%d feet", feet);
        } else if (miles < 100) {
            // If under 100 miles, add one decimal place
            out = String.format(Locale.getDefault(), "%.1f miles", miles);
        } else {
            // If over 100 miles, only display whole numbers
            out = String.format(Locale.getDefault(), "%.0f miles", miles);
        }
        return out;
    }
}
