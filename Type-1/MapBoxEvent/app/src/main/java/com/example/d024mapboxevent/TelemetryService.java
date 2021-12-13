package com.example.d024mapboxevent;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TelemetryService extends Service {



    @Override
    public void onCreate() {
        // TODO Check for location permissions here
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
     }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(null, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        });
        return null;
    }
}
