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

package com.vrem.wifianalyzer.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import com.vrem.util.BuildUtils;

import androidx.annotation.NonNull;

public class ApplicationPermission {
    static final String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION};
    static final int REQUEST_CODE = 0x123450;

    private final Activity activity;

    public ApplicationPermission(@NonNull Activity activity) {
        this.activity = activity;
    }


    public void check() {
        if (isGranted()) {
            return;
        }
        if (activity.isFinishing()) {
            return;
        }
    }

    public boolean isGranted(int requestCode, @NonNull int[] grantResults) {
        return requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    boolean isGranted() {
        return isGranted(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean isGranted(String accessFineLocation) {
        if (BuildUtils.isMinVersionM()) {
            return activity.checkSelfPermission(accessFineLocation) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

}
