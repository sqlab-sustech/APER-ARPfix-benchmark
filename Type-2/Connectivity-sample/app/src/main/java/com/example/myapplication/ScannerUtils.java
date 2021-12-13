package com.example.myapplication;

import android.app.PendingIntent;
import android.bluetooth.le.BluetoothLeScanner;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;

public class ScannerUtils {
    private BluetoothLeScanner mBluetoothLeScanner;


    //need location permission >24
    public void startScanning() {

            mBluetoothLeScanner.startScan(null, null, (PendingIntent) null);
    }
}
