package com.lucasg234.protesttracker.mainactivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import com.lucasg234.protesttracker.permissions.PermissionsHandler;
import com.lucasg234.protesttracker.util.LocationUtils;

/**
 * Fragment containing a Google MapView
 * Users can see recent posts geographically
 */
public class MapFragment extends Fragment {

    // Default zoom level on the map
    private static final int DEFAULT_ZOOM_LEVEL = 17;
    // Minimum distance change between locations. Set to 10 meters
    // If user does not move this distance, no updates will be created.
    public static final float MINIMUM_DISPLACEMENT_METERS = 10;
    // Maximum time to wait for a LocationUpdate. Set to 60 seconds
    private static final int UPDATE_INTERVAL_MS = 60000;
    // Minimum time to wait for a locationUpdate. Set to 5 seconds
    private static final int FASTEST_INTERVAL_MS = 5000;

    private static final String TAG = "MapFragment";
    private MainActivity mParent;
    // Represents whether the camera is currently follow the user's current position
    private boolean mFollowUser;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of MapFragment
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

                    loadMap(new GoogleMap(null));

    }

    // Configures the GoogleMap
    private void loadMap(GoogleMap map) {
        if (!PermissionsHandler.checkLocationPermission(mParent)) {
            PermissionsHandler.requestLocationPermission(this);
            return;
        }
        // Map follows current user's location
        map.setMyLocationEnabled(true);
        subscribeToLocationRequests(map);
    }

    // Enables automatic location requests as the user moves
    private void subscribeToLocationRequests(final GoogleMap map) {
        // Create a LocationRequest
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_INTERVAL_MS);
        locationRequest.setSmallestDisplacement(MINIMUM_DISPLACEMENT_METERS);

        if (!PermissionsHandler.checkLocationPermission(mParent)) {
            PermissionsHandler.requestLocationPermission(this);
            return;
        }
        // Call onLocationChange method when new Location is found
        LocationServices.getFusedLocationProviderClient(mParent)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {

                            }
                        },
                        Looper.myLooper());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If permission was just granted to allow location services, then restart view loading
        if (requestCode == PermissionsHandler.REQUEST_CODE_LOCATION_PERMISSIONS && permissions.length >= 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
