package com.example.wifikeysharedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.util.List;

public class WifiListActivity extends AppCompatActivity {
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//             User request for 'Location' permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 233);
        } else {
            List<WifiConfiguration> savedWifiConfigs = wifiManager.getConfiguredNetworks();
        }
    }

/*    private class WifiListTask extends AsyncTask<Void, Void, List<WifiNetwork>> {

        @Override
        protected List<WifiNetwork> doInBackground(Void... params) {

            List<WifiNetwork> wifiManagerNetworks = new ArrayList<>();
            List<WifiConfiguration> savedWifiConfigs = wifiManager.getConfiguredNetworks();
        }*/
}
