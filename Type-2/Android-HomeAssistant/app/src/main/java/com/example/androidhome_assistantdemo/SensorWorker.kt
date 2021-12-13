package com.example.androidhome_assistantdemo

import android.content.Context

class SensorWorker(
    appContext: Context
)  {

    init {
      var asui :  AllSensorsUpdaterImpl = AllSensorsUpdaterImpl( appContext)
        val sensorManagers = mutableListOf(
            NetworkSensorManager()
        )
        asui.registerSensors(sensorManagers)
    }


}
