package com.example.d047wiglenet;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GPSListener implements Listener, LocationListener {
    private static final long GPS_TIMEOUT = 15000L;
    private static final long NET_LOC_TIMEOUT = 60000L;

    private MainActivity mainActivity;
    private Location location;
    private Location networkLocation;
    private GpsStatus gpsStatus;
    // set these times to avoid NPE in locationOK() seen by <DooMMasteR>
    private Long lastLocationTime = 0L;
    private Long lastNetworkLocationTime = 0L;
    private Long satCountLowTime = 0L;
    private float previousSpeed = 0f;
    private LocationListener mapLocationListener;

    public GPSListener( MainActivity mainActivity ) {
        this.mainActivity = mainActivity;
    }

    public void setMapListener( LocationListener mapLocationListener ) {
        this.mapLocationListener = mapLocationListener;
    }

    public void setMainActivity( MainActivity mainActivity ) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onGpsStatusChanged( final int event ) {
        if ( event == GpsStatus.GPS_EVENT_STOPPED ) {
            // this event lies, on one device it gets called when the
            // network provider is disabled :(  so we do nothing...
            // listActivity.setLocationUpdates();
        }
        // MainActivity.info("GPS event: " + event);
        updateLocationData(null);
    }

    public void handleScanStop() {
        gpsStatus = null;
        location = null;
    }

    @Override
    public void onLocationChanged( final Location newLocation ) {
        // MainActivity.info("GPS onLocationChanged: " + newLocation);
        updateLocationData( newLocation );

        if ( mapLocationListener != null ) {
            mapLocationListener.onLocationChanged( newLocation );
        }
    }

    @Override
    public void onProviderDisabled( final String provider ) {

        if ( mapLocationListener != null ) {
            mapLocationListener.onProviderDisabled( provider );
        }
    }

    @Override
    public void onProviderEnabled( final String provider ) {

        if ( mapLocationListener != null ) {
            mapLocationListener.onProviderEnabled( provider );
        }
    }

    @Override
    public void onStatusChanged( final String provider, final int status, final Bundle extras ) {

        if ( mapLocationListener != null ) {
            mapLocationListener.onStatusChanged( provider, status, extras );
        }
    }

    /** newLocation can be null */
    private void updateLocationData( final Location newLocation ) {
        final LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        // see if we have new data
        gpsStatus = locationManager.getGpsStatus( gpsStatus );
        final int satCount = getSatCount();

        boolean newOK = newLocation != null;
        final boolean locOK = locationOK( location, satCount );
        final long now = System.currentTimeMillis();

        if ( newOK ) {
            if ( NETWORK_PROVIDER.equals( newLocation.getProvider() ) ) {
                // save for later, in case we lose gps
                networkLocation = newLocation;
                lastNetworkLocationTime = now;
            }
            else {
                lastLocationTime = now;
                // make sure there's enough sats on this new gps location
                newOK = locationOK( newLocation, satCount );
            }
        }



        final boolean netLocOK = locationOK( networkLocation, satCount );

        boolean wasProviderChange = false;
        if ( ! locOK ) {
            if ( newOK ) {
                wasProviderChange = true;
                //noinspection RedundantIfStatement
                if ( location != null && ! location.getProvider().equals( newLocation.getProvider() ) ) {
                    wasProviderChange = false;
                }

                location = newLocation;
            }
            else if ( netLocOK ) {
                location = networkLocation;
                wasProviderChange = true;
            }
            else if ( location != null ) {
                // transition to null
                location = null;
                wasProviderChange = true;
                // make sure we're registered for updates
                mainActivity.setLocationUpdates();
            }
        }
        else if ( newOK && GPS_PROVIDER.equals( newLocation.getProvider() ) ) {
            if ( NETWORK_PROVIDER.equals( location.getProvider() ) ) {
                // this is an upgrade from network to gps
                wasProviderChange = true;
            }
            location = newLocation;
            if ( wasProviderChange ) {
                // save it in prefs
                saveLocation();
            }
        }
        else if ( newOK && NETWORK_PROVIDER.equals( newLocation.getProvider() ) ) {
            if ( NETWORK_PROVIDER.equals( location.getProvider() ) ) {
                // just a new network provided location over an old one
                location = newLocation;
            }
        }


    }



    private boolean locationOK( final Location location, final int satCount ) {
        boolean retval = false;
        final long now = System.currentTimeMillis();

        //noinspection StatementWithEmptyBody
        if ( location == null ) {
            // bad!
        }
        else if ( GPS_PROVIDER.equals( location.getProvider() ) ) {
            if ( satCount > 0 && satCount < 3 ) {
                if ( satCountLowTime == null ) {
                    satCountLowTime = now;
                }
            }
            else {
                // plenty of sats
                satCountLowTime = null;
            }
            boolean gpsLost = satCountLowTime != null && (now - satCountLowTime) > GPS_TIMEOUT;
            gpsLost |= now - lastLocationTime > GPS_TIMEOUT;
            gpsLost |= horribleGps(location);
            retval = ! gpsLost;
        }
        else if ( NETWORK_PROVIDER.equals( location.getProvider() ) ) {
            boolean gpsLost = now - lastNetworkLocationTime > NET_LOC_TIMEOUT;
            gpsLost |= horribleGps(location);
            retval = ! gpsLost;
        }

        return retval;
    }

    private boolean horribleGps(final Location location) {
        // try to protect against some horrible gps's out there
        // check if accuracy is under 10 miles
        boolean horrible = location.hasAccuracy() && location.getAccuracy() > 16000;
        horrible |= location.getLatitude() < -90 || location.getLatitude() > 90;
        horrible |= location.getLongitude() < -180 || location.getLongitude() > 180;
        return horrible;
    }

    public int getSatCount() {
        int satCount = 0;
        if ( gpsStatus != null ) {
            for ( GpsSatellite sat : gpsStatus.getSatellites() ) {
                if ( sat.usedInFix() ) {
                    satCount++;
                }
            }
        }
        return satCount;
    }

    public void saveLocation() {
        // save our location for use on later runs
    }

    public Location getLocation() {
        return location;
    }

}
