//This Handy-Dandy class acquired and tweaked from http://stackoverflow.com/a/3145655/309558
package com.example.d044wordpress;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LocationHelper {
    Timer mTimer;
    LocationManager mLocationManager;
    LocationResult mLocationResult;

    public boolean getLocation(Activity activity, LocationResult result) {

        mLocationResult = result;
        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        // exceptions will be thrown if provider is not permitted.
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            mTimer.cancel();
            mLocationResult.gotLocation(location);
            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            mTimer.cancel();
            mLocationResult.gotLocation(location);
            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

/*    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            mLocationManager.removeUpdates(locationListenerGps);
            mLocationManager.removeUpdates(locationListenerNetwork);

            Location net_loc = null, gps_loc = null;
            if (mGpsEnabled) {
                gps_loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (mNetworkEnabled) {
                net_loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            // if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime()) {
                    mLocationResult.gotLocation(gps_loc);
                } else {
                    mLocationResult.gotLocation(net_loc);
                }
                return;
            }

            if (gps_loc != null) {
                mLocationResult.gotLocation(gps_loc);
                return;
            }
            if (net_loc != null) {
                mLocationResult.gotLocation(net_loc);
                return;
            }
            mLocationResult.gotLocation(null);
        }
    }*/

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }

/*    public void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mLocationManager.removeUpdates(locationListenerGps);
            mLocationManager.removeUpdates(locationListenerNetwork);
        }
    }*/
}
