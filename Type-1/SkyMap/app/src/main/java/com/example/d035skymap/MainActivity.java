package com.example.d035skymap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    GooglePlayServicesChecker playServicesChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        playServicesChecker = new GooglePlayServicesChecker();
//        playServicesChecker.maybeCheckForGooglePlayServices();
        LocationController locationController = new LocationController(this);
        locationController.start();
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