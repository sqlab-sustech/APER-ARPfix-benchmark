package com.example.photomanagerdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    PermissionManager permissionManager = new PermissionManager(this,this,new PermissionsUtils());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionManager.onMethodCall();
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            AndroidQDBUtils.INSTANCE.getExif(this, "");
/*        }
        else
        {
            // Not Q
        }*/
    }
}