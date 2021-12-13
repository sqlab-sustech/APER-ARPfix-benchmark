package com.example.d029device_info;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class DeviceModule {
    public String getSerialNumberSync(Context context) {
        try {
//            if (Build.VERSION.SDK_INT >= 26) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    return Build.getSerial();
//                }
            }
        } catch (Exception e) {
            // This is almost always a PermissionException. We will log it but return unknown
            System.err.println("getSerialNumber failed, it probably should not be used: " + e.getMessage());
        }

        return "unknown";
    }
}
