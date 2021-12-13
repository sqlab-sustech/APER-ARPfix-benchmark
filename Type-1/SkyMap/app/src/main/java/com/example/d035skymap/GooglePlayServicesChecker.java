package com.example.d035skymap;
import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


/**
 * Created by johntaylor on 4/2/16.
 */
public class GooglePlayServicesChecker implements LocationPermissionRationaleFragment.Callback {


    /**
     * Checks whether play services is available and up to date and prompts the user
     * if necessary.
     * <p/>
     * Note that at present we only need it for location services so if the user is setting
     * their location manually we don't do the check.
     */
    public void maybeCheckForGooglePlayServices() {

        checkLocationServicesEnabled();
    }

    private void checkLocationServicesEnabled() {
        if (ActivityCompat.checkSelfPermission(null, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions now
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    null, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                requestLocationPermission();
            }
        } else {

        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(null,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
               233);
    }

    /**
     * Called after a request to check permissions.
     */
    public void runAfterPermissionsCheck(int requestCode,
                                         String[] permissions,
                                         int[] grantResults) {
        if (grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          } else {
             // TODO(jontayler): Send them to the location dialog;
        }
    }

    /**
     * Called after the user is prompted to resolve any issues.
     */
    public void runAfterDialog() {
        // Just log for now.
//        Log.d(TAG, "Play Services Dialog has been shown");
    }

    public void done() {
//        Log.d(TAG, "Location rationale Dialog has been shown");
        requestLocationPermission();
    }
}
