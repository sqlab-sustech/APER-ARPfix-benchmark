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

package com.vrem.wifianalyzer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.view.LayoutInflater;

import com.vrem.util.BuildUtils;
import com.vrem.wifianalyzer.settings.Settings;
import com.vrem.wifianalyzer.wifi.scanner.ScannerService;
import com.vrem.wifianalyzer.wifi.scanner.ScannerServiceFactory;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


public enum MainContext {
    INSTANCE;


    void initialize(@NonNull MainActivity mainActivity) {
        Context applicationContext = mainActivity.getApplicationContext();
        WifiManager wifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
        Handler handler = new Handler();
        Settings currentSettings = new Settings();

        ScannerService scannerService = ScannerServiceFactory.makeScannerService(wifiManager, handler, currentSettings);
//        if(BuildUtils.isMinVersionQ()&& ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            scannerService.update();
//        }
    }

}
