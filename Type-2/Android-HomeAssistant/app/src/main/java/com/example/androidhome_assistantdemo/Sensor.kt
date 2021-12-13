package com.example.androidhome_assistantdemo
data class Sensor<T>(
    val uniqueId: String,
    val state: T,
    val type: String,
    val icon: String,
    val attributes: Map<String, Any>

)
