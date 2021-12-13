package com.example.d047wiglenet;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {
    public static class State {
        GPSListener gpsListener;
    }
    private State state;
    private static MainActivity mainActivity;
    private static final int LOCATION_PERMISSIONS_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setupLocationPermissions();//检查获取权限
        state = new State();
        state.gpsListener.setMainActivity(this);
        setupLocation();
    }
    public GPSListener getGPSListener() {
        return state.gpsListener;
    }
    public void setLocationUpdates() {
        final long setPeriod = 0;
        setLocationUpdates(setPeriod, 0f);
    }
    public void setLocationUpdates(final long updateIntervalMillis, final float updateMeters) {
        try {
            internalSetLocationUpdates(updateIntervalMillis, updateMeters);
        }
        catch (final SecurityException ex) {

        }
    }
    private void setupLocationPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            final List<String> permissionsNeeded = new ArrayList<>();
            final List<String> permissionsList = new ArrayList<>();
            if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissionsNeeded.add(mainActivity.getString(R.string.gps_permission));
            }
            if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissionsNeeded.add(mainActivity.getString(R.string.cell_permission));
            }

            if (!permissionsList.isEmpty()) {
                // The permission is NOT already granted.
                // Check if the user has been asked about this permission already and denied
                // it. If so, we want to give more explanation about why the permission is needed.
                if (!permissionsNeeded.isEmpty()) {
                    String message = mainActivity.getString(R.string.please_allow);
                    for (int i = 0; i < permissionsNeeded.size(); i++) {
                        if (i > 0) message += ", ";
                        message += permissionsNeeded.get(i);
                    }

                    // Show our own UI to explain to the user why we need to read the contacts
                    // before actually requesting the permission and showing the default UI
                    Toast.makeText(mainActivity, message, Toast.LENGTH_LONG).show();
                }

                // Fire off an async request to actually get the permission
                // This will show the standard permission request dialog UI
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        LOCATION_PERMISSIONS_REQUEST);
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String permissions[], final int[] grantResults) {
        return;
    }

    private void setupLocation() {
        handleScanChange();
    }
    private void handleScanChange() {
        this.setLocationUpdates(0, 0f);
    }
    @Override
    public void finish() {
        if (state.gpsListener != null) {
            // save our location for later runs
            state.gpsListener.saveLocation();
        }
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( state.gpsListener != null ) {
            locationManager.removeGpsStatusListener( state.gpsListener );
            try {
                locationManager.removeUpdates(state.gpsListener);
            }
            catch (final SecurityException ex) {

            }
        }
        super.finish();
    }

    private void internalSetLocationUpdates(final long updateIntervalMillis, final float updateMeters)
            throws SecurityException {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( state.gpsListener != null ) {
            // remove any old requests
            locationManager.removeUpdates( state.gpsListener );
            locationManager.removeGpsStatusListener( state.gpsListener );
        }

        // create a new listener to try and get around the gps stopping bug
        state.gpsListener = new GPSListener( this );
//        try {
            locationManager.addGpsStatusListener(state.gpsListener);
//        }
//        catch (final SecurityException ex) {
//        }

        final List<String> providers = locationManager.getAllProviders();
        if (providers != null) {
            for ( String provider : providers ) {
                if ( ! "passive".equals( provider ) && updateIntervalMillis > 0 )
//                    try {
                        locationManager.requestLocationUpdates(provider, updateIntervalMillis, updateMeters, state.gpsListener);
//                    }
//                    catch (final SecurityException ex) {
//                    }
                }
            }
        }
    }