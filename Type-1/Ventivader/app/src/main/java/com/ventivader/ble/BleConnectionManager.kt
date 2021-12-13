package com.ventivader.ble

import android.app.Application
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresApi
import java.nio.charset.Charset
import java.util.*


class BleConnectionManager(private val application: Application) {


    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun scanLeDevices() {
            bluetoothAdapter?.bluetoothLeScanner?.startScan(null,
                ScanSettings.Builder().build(), null)
    }

}
