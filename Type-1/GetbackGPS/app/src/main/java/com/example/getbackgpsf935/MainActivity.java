package com.example.getbackgpsf935;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private LocationService mService;
    private static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mService = new LocationService();
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        refresh(item);
        return super.onOptionsItemSelected(item);
    }

    public final void refresh(final MenuItem item) {
//        mService.updateLocationProvider();
        mService.updateLocation();
        /*if (!mService.isLocationPermissionGranted()) {
            requestLocationPermission();
        }*/
    }

    protected boolean refreshDisplay() {

        /*if (!mService.isLocationPermissionGranted()) {
            requestLocationPermission();
        }*/

        refreshCrouton();

        return true;
    }

    protected final void refreshCrouton() {
        //null
    }

    // async check & request
    protected final void requestLocationPermission() {
        if (mService == null) {
            return;
        }

        // request permission if location permission is not granted
        if (!mService.isLocationPermissionGranted()) {

            // Permission is not granted
            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        PERMISSIONS_LOCATION,
                        0);
            }
        }
    }

    //call back
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            refreshDisplay();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}