package com.example.d034Evercam;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class Permission {
    public final static String LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public final static int REQUEST_CODE_LOCATION = 400;

    public static boolean isGranted(Activity activity, String permission)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static void request(Activity activity, String[] permissions, int requestCode)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            activity.requestPermissions(permissions, requestCode);
        }
    }
}
