package com.example.RNWifiP2p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // use
        WiFiP2PManagerModule managerModule = new WiFiP2PManagerModule();
        managerModule.getAvailablePeersList();

        // use
        WiFiP2PBroadcastReceiver broadcastReceiver = new WiFiP2PBroadcastReceiver();
        broadcastReceiver.onReceive(this,new Intent());
    }
}