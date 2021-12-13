package com.example.d037collectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private PermissionUtils permissionUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* permissionUtils = new PermissionUtils();
        if(!permissionUtils.isReadPhoneStatePermissionGranted(this))
        {
            permissionUtils.requestLocationPermissions(this);
        }*/
        AppDependencyModule appDependencyModule = new AppDependencyModule();
        appDependencyModule.getLine1Number(this);
    }
}