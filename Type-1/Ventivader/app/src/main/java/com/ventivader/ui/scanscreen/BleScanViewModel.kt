package com.ventivader.ui.scanscreen

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.ventivader.VentivaderApplication


class BleScanViewModel(application: Application) : AndroidViewModel(application) {



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun findAndConnectBleDevice() {

        val bluetoothConnectionManager = bluetoothConnectionManager()

        bluetoothConnectionManager.scanLeDevices()

    }

    private fun bluetoothConnectionManager() =
            getApplication<VentivaderApplication>().bleConnectionManager


}