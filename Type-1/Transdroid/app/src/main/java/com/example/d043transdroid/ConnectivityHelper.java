package com.example.d043transdroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ConnectivityHelper {
    protected WifiManager wifiManager;

    public String getConnectedNetworkName() {
        if (wifiManager != null && wifiManager.getConnectionInfo() != null && wifiManager.getConnectionInfo().getSSID() != null) {
            return wifiManager.getConnectionInfo().getSSID().replace("\"", "");
        }
        return null;
    }


    public boolean hasNetworkNamePermission(final Context activityContext) {
        return ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public void askNetworkNamePermission(final Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    public boolean requestedPermissionWasGranted(int requestCode, String[] permissions, int[] grantResults) {
        return (requestCode == 0
                && permissions != null
                && grantResults != null
                && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED);
    }

}
