package com.example.RNWifiP2p;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by zyusk on 01.05.2018.
 */
public class WiFiP2PManagerModule implements WifiP2pManager.ConnectionInfoListener {
    private WifiP2pInfo wifiP2pInfo;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private final IntentFilter intentFilter = new IntentFilter();
//    private WiFiP2PDeviceMapper mapper = new WiFiP2PDeviceMapper();

    public WiFiP2PManagerModule() {
    }
    public void getAvailablePeersList() {
        Activity activity = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission((Context)null, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    233);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else {
            manager.requestPeers(channel, new PeerListListener() {
                @Override
                public void onPeersAvailable(WifiP2pDeviceList deviceList) {
                /*WritableMap params = mapper.mapDevicesInfoToReactEntity(deviceList);
                promise.resolve(params);*/
                }
            });
        }
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {

    }
}
