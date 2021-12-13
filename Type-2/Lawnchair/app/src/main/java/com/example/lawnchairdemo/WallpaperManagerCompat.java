/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lawnchairdemo;

import android.app.Activity;
import android.app.WallpaperManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import static android.app.WallpaperManager.FLAG_SYSTEM;

public class WallpaperManagerCompat {

    private static final Object sInstanceLock = new Object();
    private static WallpaperManagerCompat sInstance;

    public static WallpaperManagerCompat getInstance(Context context) {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                context = context.getApplicationContext();
                sInstance = new WallpaperManagerCompat(context);
            }
            return sInstance;
        }
    }

    private final Context mContext;

    public WallpaperManagerCompat(Context context) {
            mContext = context;
        if (Build.VERSION.SDK_INT >= 27 && ActivityCompat.checkSelfPermission(mContext,"android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,new String[]{"android.permission.READ_EXTERNAL_STORAGE"},0);
            return;
        }
            reloadColors();
    }


    private void reloadColors() {
                new ComponentName(mContext, ColorExtractionService.class);
    }


    /**
     * Intent service to handle color extraction
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static class ColorExtractionService extends JobService implements Runnable {

        /**
         * Extracts the wallpaper colors and sends the result back through the receiver.
         */
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            WallpaperManager wm = WallpaperManager.getInstance(this);
            wm.getWallpaperFile(FLAG_SYSTEM);
        }

        @Override
        public boolean onStartJob(JobParameters jobParameters) {
            return false;
        }

        @Override
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }
}
