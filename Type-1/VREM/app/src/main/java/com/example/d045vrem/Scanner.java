/*
 * WiFiAnalyzer
 * Copyright (C) 2018  VREM Software Development <VREMSoftwareDevelopment@gmail.com>
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

package com.example.d045vrem;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

class Scanner implements ScannerService {
    private final WifiManager wifiManager;

    Scanner (WifiManager wifiManager) {

        this.wifiManager = wifiManager;

    }

    @Override
    public void update() {
        scanResults();

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void resume() {

    }

    @Override
    public void setWiFiOnExit() {

    }

    private void scanResults() {
        try {
            if (wifiManager.startScan()) {
                List<ScanResult> scanResults = wifiManager.getScanResults();
            }
        } catch (Exception e) {
            // critical error: do not die
        }
    }
/*
    private WifiInfo wiFiInfo() {
        try {
            return wifiManager.getConnectionInfo();
        } catch (Exception e) {
            // critical error: do not die
            return null;
        }
    }

    private List<WifiConfiguration> wifiConfiguration() {
        try {
            return wifiManager.getConfiguredNetworks();
        } catch (Exception e) {
            // critical error: do not die
            return new ArrayList<>();
        }
    }*/

}
