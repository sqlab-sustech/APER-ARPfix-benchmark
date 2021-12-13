package com.example.d035skymap;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class DynamicStarMapActivity extends AppCompatActivity {
    GooglePlayServicesChecker playServicesChecker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playServicesChecker = new GooglePlayServicesChecker();
        playServicesChecker.maybeCheckForGooglePlayServices();
        LocationController locationController = new LocationController(this);
//        locationController
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 233) {
            playServicesChecker.runAfterPermissionsCheck(requestCode, permissions, grantResults);
            return;
        }
    }
}
