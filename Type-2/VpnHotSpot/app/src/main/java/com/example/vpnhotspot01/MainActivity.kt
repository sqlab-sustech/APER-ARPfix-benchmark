package com.example.vpnhotspotdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //RepeaterService doStart
        if (Build.VERSION.SDK_INT >= 29 && checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 4)
            return
        } else
            startService(Intent(this, RepeaterService::class.java))
    }
//        ContextCompat.startForegroundService(this, Intent(this, RepeaterService::class.java))
}

