package com.example.photomanagerdemo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import java.util.*

class PermissionManager (
        private val applicationContext: Context,
        var activity: Activity?,
        private val permissionsUtils: PermissionsUtils
)  {

     fun onMethodCall() {

        var needLocationPermissions = false

        var permissions = mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (needLocationPermissions && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_MEDIA_LOCATION)
        }
        //Check & Request
         permissionsUtils.getPermissions(activity,2333, permissions)

    }
}