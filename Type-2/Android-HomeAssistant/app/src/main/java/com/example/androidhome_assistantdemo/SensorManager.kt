package com.example.androidhome_assistantdemo

import android.content.Context

interface SensorManager {

    fun getSensorRegistrations(context: Context): List<SensorRegistration<Any>>

    fun getSensors(context: Context): List<Sensor<Any>>
}
