package com.lucasg234.protesttracker.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lucasg234.protesttracker.R;
import com.lucasg234.protesttracker.databinding.ActivityMainBinding;
import com.lucasg234.protesttracker.permissions.NoPermissionsFragment;
import com.lucasg234.protesttracker.permissions.PermissionsHandler;

/**
 * Central activity which holds the FeedFragment, MapFragment, ComposeFragment, and SettingsFragment
 * Handles navigation between the Fragments
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mBinding;
    private Fragment mCurrentFragment;
    private boolean mNavigationEnabled;
    private int mNumProcesses;

    private FeedFragment mFeed;
    private ComposeFragment mCompose;
    private MapFragment mMap;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mNumProcesses = 0;

        setSupportActionBar(mBinding.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (!PermissionsHandler.checkLocationPermission(this)) {
        Log.i(TAG, "Found no location permissions");
        mNavigationEnabled = false;
        disableFragmentNavigation();
        } else {
        mNavigationEnabled = true;
        enableFragmentNavigation();
        }
    }

    // Method called when Activity is reopened from another program
    // Respond here in case user has changed their permission settings from another location
    @Override
    protected void onResume() {
        super.onResume();

        boolean locationPermissions = PermissionsHandler.checkLocationPermission(this);
//        boolean locationPermissions = true;
        if (mNavigationEnabled && !locationPermissions) {
            disableFragmentNavigation();
        } else if (!mNavigationEnabled && locationPermissions) {
            enableFragmentNavigation();
        }
    }

    // Enables the bottom navigation and all fragments connected to it
    public void enableFragmentNavigation() {
        // All fragments constructed only once when BottomNavigation enabled
        mFeed = FeedFragment.newInstance();
        mCompose = ComposeFragment.newInstance();
        mMap = MapFragment.newInstance();

        final FragmentManager fragmentManager = getSupportFragmentManager();


        // If navigation was previously disabled, removed the NoPermissionsFragment
        if (!mNavigationEnabled) {
            fragmentManager.beginTransaction().remove(mCurrentFragment).commit();
        }

        // Add all initial fragments
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, mCompose, ComposeFragment.class.getSimpleName()).hide(mCompose).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, mFeed, FeedFragment.class.getSimpleName()).hide(mFeed).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, mMap, MapFragment.class.getSimpleName()).commit();

        mCurrentFragment = mMap;
        mNavigationEnabled = true;
    }

    // Disable the BottomNavigationView and change the fragment to NoPermissionsFragment
    public void disableFragmentNavigation() {
        // If navigation was previously disabled, hide the current fragment
        if (mNavigationEnabled) {
            getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
        }
        // Create a NoPermissionsFragment as the current fragment
        NoPermissionsFragment noPermissionsFragment = NoPermissionsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentHolder, noPermissionsFragment, NoPermissionsFragment.class.getSimpleName()).commit();
        mCurrentFragment = noPermissionsFragment;

        // Disable the onClickListener for the BottomNavigationView
        mBinding.mainBottomNavigation.setOnClickListener(null);

        mNavigationEnabled = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionsHandler.REQUEST_CODE_LOCATION_PERMISSIONS &&
                PermissionsHandler.checkLocationPermission(this)) {
        enableFragmentNavigation();
        }
    }

}

