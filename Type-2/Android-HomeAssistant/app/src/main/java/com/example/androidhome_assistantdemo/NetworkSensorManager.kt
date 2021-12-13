package com.example.androidhome_assistantdemo

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log

class NetworkSensorManager : SensorManager {
    companion object {
        private const val TAG = "NetworkSM"
    }

    override fun getSensorRegistrations(context: Context): List<SensorRegistration<Any>> {
        val sensorRegistrations = mutableListOf<SensorRegistration<Any>>()

        getWifiConnectionSensor(context)?.let {
            sensorRegistrations.add(
                SensorRegistration(
                    it,
                    "Wifi Connection"
                )
            )
        }

        return sensorRegistrations
    }

    override fun getSensors(context: Context): List<Sensor<Any>> {
        val sensors = mutableListOf<Sensor<Any>>()

        getWifiConnectionSensor(context)?.let {
            sensors.add(it)
        }

        return sensors
    }

    private fun getWifiConnectionSensor(context: Context): Sensor<Any>? {
        if (!PermissionManager.checkLocationPermission(context)) {
            Log.w(TAG, "Tried getting wifi info without permission.")
            return null
        }
        val wifiManager =
            (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
        val conInfo = wifiManager.connectionInfo

        val ssid = if (conInfo.networkId == -1) {
            "<not connected>"
        } else {
            conInfo.ssid.removePrefix("\"").removeSuffix("\"")
        }

        wifiManager.scanResults.firstOrNull {
            it.BSSID == conInfo.bssid
        }?.level ?: -1
        return null
    }

}
