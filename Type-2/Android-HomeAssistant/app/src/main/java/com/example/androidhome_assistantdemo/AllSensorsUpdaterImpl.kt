package com.example.androidhome_assistantdemo

import android.content.Context
import android.util.Log

class AllSensorsUpdaterImpl(
    private val appContext: Context
) : SensorUpdater {
    companion object {
        private const val TAG = "AllSensorsUpdaterImpl"
    }

    override suspend fun updateSensors() {
        val sensorManagers = mutableListOf(
            NetworkSensorManager()
        )
        sensorManagers.flatMap { it.getSensors(appContext) }.toTypedArray()

    }

    public  fun registerSensors(sensorManagers: List<SensorManager>) {

        sensorManagers.flatMap {
            it.getSensorRegistrations(appContext)
        }
    }
}
