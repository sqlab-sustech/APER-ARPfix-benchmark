package com.example.d044wordpress;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class EditPostSettingsFragment extends Fragment implements View.OnClickListener {
    private View mLocationAddSection;
    private View mLocationSearchSection;
    private View mLocationViewSection;
    private ViewGroup mRootView;
    private LocationHelper mLocationHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(null, container, false);
        initLocation();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("ResourceType")
    public void initLocation() {
        View locationRootView = ((ViewStub) mRootView.findViewById(1)).inflate();
        mLocationAddSection = locationRootView.findViewById(1);
        mLocationSearchSection = locationRootView.findViewById(1);
        mLocationViewSection = locationRootView.findViewById(1);
    }

    @Override
    public void onClick(View v) {
//        if (checkForLocationPermission()) {
//            showLocationSearch();
            searchLocation();
//        }
    }


    public void showLocationSearch() {
        //应该是封装在了Location Healper
        mLocationAddSection.setVisibility(View.GONE);
        mLocationSearchSection.setVisibility(View.VISIBLE);
        mLocationViewSection.setVisibility(View.GONE);
//        EditTextUtils.showSoftInput(mLocationEditText);
    }

    private void searchLocation() {
        fetchCurrentLocation();
    }

    private void fetchCurrentLocation() {
        if (!isAdded()) {
            return;
        }
        if (mLocationHelper == null) {
            mLocationHelper = new LocationHelper();
        }
        mLocationHelper.getLocation(getActivity(), locationResult);

    }
    private LocationHelper.LocationResult locationResult = new LocationHelper.LocationResult() {
        @Override
        public void gotLocation(final Location location) {
            if (getActivity() == null)
                return;
            // note that location will be null when requesting location fails
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
//                    setLocation(location);
                }
            });
        }
    };
    private boolean checkForLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is missing and must be requested.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return false;
        }
        return true;
    }


}
