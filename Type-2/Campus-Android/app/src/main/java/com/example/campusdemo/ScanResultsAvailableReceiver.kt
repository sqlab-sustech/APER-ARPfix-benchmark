package de.tum.`in`.tumcampusapp.service

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION
import android.os.Build
import androidx.core.content.ContextCompat.checkSelfPermission
import org.jetbrains.anko.wifiManager

/**
 * Listens for android's ScanResultsAvailable broadcast and checks if eduroam is nearby.
 * If yes and eduroam has not been setup by now it shows an according notification.
 */
class ScanResultsAvailableReceiver : BroadcastReceiver() {

    /**
     * This method either gets called by broadcast directly or gets repeatedly triggered by the
     * WifiScanHandler, which starts scans at time periods, as long as an eduroam or lrz network is
     * visible. onReceive then continues to store information like dBm and SSID to the local database.
     * The SyncManager then takes care of sending the Wifi measurements to the server in a given time
     * interval.
     */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != SCAN_RESULTS_AVAILABLE_ACTION) {
            return
        }

        if (!context.wifiManager.isWifiEnabled) {
            return
        }

        // Check if locations are enabled
        val locationsEnabled =
                checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
        if (!locationsEnabled) {
            // Stop here as wifi.getScanResults will either return an empty list or throw an
            // exception (on android 6.0.0)
            return
        }


        context.wifiManager.scanResults
    }


}
