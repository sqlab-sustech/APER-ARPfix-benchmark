/**
 * Location Service
 * <p>
 * Copyright (C) 2012-2018 Dieter Adriaenssens
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @package com.github.ruleant.getback_gps
 * @author Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
package com.example.getbackgpsf935;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Location Service provides the current location.
 *
 * This service will connect to the Location Provider
 * and retrieves the current location
 *
 * @author Dieter Adriaenssens <ruleant@users.sourceforge.net>
 */
public class LocationService extends Service {
    /**
     * SharedPreferences location for StoredDestination.
     */
    public static final String PREFS_STORE_DEST = "stored_destination";

    /**
     * SharedPreferences location for last known good location.
     */
    public static final String PREFS_LAST_LOC = "last_location";

    /**
     * SharedPreferences location for previous location.
     */
    public static final String PREFS_PREV_LOC = "prev_location";


    /**
     * Current context.
     */
    private final Context mContext = this;

    /**
     * LocationManager instance.
     */
    private LocationManager mLocationManager;
    /**
     * Name of the LocationProvider.
     */
    private String mProviderName = "";

    @Override
    public final void onCreate() {
        mLocationManager
                = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);


        // mProviderName is set by updateLocationProvider
//        updateLocationProvider();
        // and used in requestUpdatesFromProvider, which sets location
        requestUpdatesFromProvider();
//        updateLocation();
    }


    @Override
    public final int onStartCommand(
            final Intent intent, final int flags, final int startId) {
        // The service is starting, due to a call to startService()
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Retrieve Location Provider.
     *
     * Define best location provider based on certain criteria
     */
/*    public final void updateLocationProvider() {
        Criteria criteria = new Criteria();

        if (isLocationPermissionGranted()) {
            mProviderName = mLocationManager.getBestProvider(criteria, true);
        }
    }*/

/*
    public final boolean isSetLocationProvider() {
        return mProviderName != null && mProviderName.length() > 0;
    }*/

    /**
     * Checks if permission ACCESS_FINE_LOCATION is granted.
     *
     * @return boolean true if permission ACCESS_FINE_LOCATION is granted.
     */
    public final boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        } else {
            return true;
        }
    }

    /**
     * Update Location.
     *
     * Force location update, using getLastKnownLocation()
     */
    public final void updateLocation() {
        /*if (!isLocationPermissionGranted()) {
            return;
        }*/

        // update location using getLastKnownLocation,
        // don't wait for listener update
        try {
            mLocationManager.getLastKnownLocation(mProviderName);
        } catch (SecurityException e) {

        }
    }

    private boolean requestUpdatesFromProvider() {
        /*if (!isLocationPermissionGranted()) {
            return false;
        }*/
        Location location = null;
        try {
            LocationListener mListener = null;
            mLocationManager.requestLocationUpdates(
                    mProviderName,
                    0,
                    0,
                    mListener);
            location = mLocationManager.getLastKnownLocation(mProviderName);
        } catch (SecurityException e) {

        }
        return false;
    }

    @Override
    public void onDestroy() {
        mLocationManager.removeUpdates(mListener);
        super.onDestroy();
    }

    private final LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
