package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ScannerUtils scannerUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ScannerFragment

/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //start scanning
                scannerUtils.startScanning();
                if(checkLocationPermission()) {
                }
                else
                {
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            233
                    );
                }
            }
            else*/
            {
                //start scanning
                scannerUtils.startScanning();
            }

    }

    private boolean checkLocationPermission() {
        boolean hasLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
        return hasLocationPermission;
    }
}