package com.example.RNWifiP2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by zyusk on 15.07.2018.
 */
public class WiFiP2PBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
//    private WiFiP2PDeviceMapper mapper = new WiFiP2PDeviceMapper();

    public WiFiP2PBroadcastReceiver() {
        super();

//        this.manager = manager;
//        this.channel = channel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            WifiP2pManager.PeerListListener peerListListener = null;
            manager.requestPeers(channel, peerListListener);
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                WifiP2pManager.ConnectionInfoListener connectionListener = null;
                manager.requestConnectionInfo(channel, connectionListener);
            }
        }
    }

}
