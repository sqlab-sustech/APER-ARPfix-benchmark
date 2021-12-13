/*
 * WiFiAnalyzer
 * Copyright (C) 2019  VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.vrem.wifianalyzer.wifi.scanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import com.vrem.util.BuildUtils;
import com.vrem.wifianalyzer.settings.Settings;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

class Scanner implements ScannerService {
    private final WifiManager wifiManager;
    private final Settings settings;

    Scanner(@NonNull WifiManager wifiManager, @NonNull Handler handler, @NonNull Settings settings) {
        this.wifiManager = wifiManager;
        this.settings = settings;
    }

    @Override
    public void update() {
//              scanResults();
            wifiConfiguration();

    }

    @Override
    public void pause() {

    }
    private void scanResults() {
        try {
            if (wifiManager.startScan()) {
                List<ScanResult> scanResults = wifiManager.getScanResults();
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            }
        } catch (Exception e) {
            // critical error: do not die
        }
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }


    // getConfiguredNetworks need location permission in Android 10
    private List<WifiConfiguration> wifiConfiguration() {
            return wifiManager.getConfiguredNetworks();
    }

}
