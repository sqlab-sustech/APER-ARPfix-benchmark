package com.example.mapboxdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

public class TelemetryUtils {


    @SuppressLint("MissingPermission")
    @NonNull
    public static String obtainCellularNetworkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int output = TelephonyManager.NETWORK_TYPE_UNKNOWN;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                output = telephonyManager.getDataNetworkType();
            } catch (SecurityException se) {
                // Developer did not add READ_PHONE_STATE permission to their app
                // or user did not accept the permission.
            }
//        } else {
            output = telephonyManager.getNetworkType();
//        }
        return "";
    }
}
