package com.example.wififlutterdemo;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

public class WifiInfoPlugin {
    private WifiManager moWiFi;
    private Context moContext;
    private Activity moActivity;
    private BroadcastReceiver receiver;
    private List<String> ssidsToBeRemovedOnExit = new ArrayList<String>();

    public void onMethodCall() {
        findAndConnect();
    }
    /// Send the ssid and password of a Wifi network into this to connect to the network.
    /// Example:  wifi.findAndConnect(ssid, password);
    /// After 10 seconds, a post telling you whether you are connected will pop up.
    /// Callback returns true if ssid is in the range
    private void findAndConnect() {
        int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 65655434;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && moContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            moActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
        }
        new Thread() {
            public void run() {

                moWiFi.getScanResults();
            }
        }.start();
    }

}
