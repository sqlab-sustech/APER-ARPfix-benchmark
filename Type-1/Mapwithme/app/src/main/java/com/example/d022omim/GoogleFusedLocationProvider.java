package com.example.d022omim;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class GoogleFusedLocationProvider extends BaseLocationProvider
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private final GoogleApiClient mGoogleApiClient;

    private PendingResult<LocationSettingsResult> mLocationSettingsResult;


    GoogleFusedLocationProvider(@NonNull LocationFixChecker locationFixChecker,
                                @NonNull Context context) {
        super(locationFixChecker);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    protected void start() {
        if (mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) {
            setActive(true);
            return;
        }

        mGoogleApiClient.connect();
        setActive(true);
    }

    @Override
    protected void stop() {
        if (mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (PendingIntent) null);

        if (mLocationSettingsResult != null && !mLocationSettingsResult.isCanceled())
            mLocationSettingsResult.cancel();

        mGoogleApiClient.disconnect();
        setActive(false);
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkSettingsAndRequestUpdates();
    }

    private void checkSettingsAndRequestUpdates() {
        mLocationSettingsResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, null);
        mLocationSettingsResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                requestLocationUpdates();
            }
        });
    }


    @SuppressWarnings("MissingPermission")
// A permission is checked externally
    private void requestLocationUpdates() {
        if (!mGoogleApiClient.isConnected())
            return;


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, null, (PendingIntent) null);
        Location last = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {
        setActive(false);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        setActive(false);
    }
}