package com.lucasg234.protesttracker.mainactivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lucasg234.protesttracker.permissions.PermissionsHandler;
import com.lucasg234.protesttracker.util.LocationUtils;

import java.io.File;


/**
 * Fragment where user can create posts
 * Saves posts to Parse
 */
public class ComposeFragment extends Fragment {
    private static final String TAG = "ComposeFragment";


    private File mInternalImageStorage;

    // Stores whether mInternalImageStorage is from the gallery or camera
    // if mInternalImageStorage == null, value is undefined
    private boolean mInternalImageFromGallery;

    public ComposeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of ComposeFragment
     */
    public static ComposeFragment newInstance() {
        return new ComposeFragment();
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

                    savePost();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    // Constructs Post object and saves it to the Parse server
    private void savePost() {

        // Ensure location permissions before attempting to make post
        if (!PermissionsHandler.checkLocationPermission(null)) {
            Log.i(TAG, "Cancelling post save to ask for permissions");
            PermissionsHandler.requestLocationPermission(this);
            return;
        }

        // Next ensure current location can be found
        Location currentLocation = LocationUtils.getCurrentLocation(null);

    }


    // Ensure no floating storage left on fragment deletion
    @Override
    public void onDestroy() {
        if (mInternalImageStorage != null) {
            mInternalImageStorage.delete();
            mInternalImageStorage = null;
        }
        super.onDestroy();
    }

}