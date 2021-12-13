package com.example.vpnhotspotdemo

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.net.wifi.p2p.*
import android.os.IBinder

/**
 * Service for handling Wi-Fi P2P. `supported` must be checked before this service is started otherwise it would crash.
 * ContextCompat.startForegroundService(context, Intent(context, RepeaterService::class.java))
 */
class RepeaterService : Service(), WifiP2pManager.ChannelListener, SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        /**
         * Placeholder for bypassing networkName check.
         */
        private const val PLACEHOLDER_NETWORK_NAME = "DIRECT-00-VPNHotspot"

        /**
         * This is only a "ServiceConnection" to system service and its impact on system is minimal.
         */
        private val p2pManager: WifiP2pManager? by lazy {
            try {
               null
            } catch (e: RuntimeException) {
                null
            }
        }
    }

    // **
    private val p2pManager get() = Companion.p2pManager!!

    private var channel: WifiP2pManager.Channel? = null
    // **

    /**
     * startService Step 1
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        p2pManager.requestGroupInfo(channel) {
                doStart()
        }
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    /**
     * startService Step 2 (if a group isn't already available)
     */
    private fun doStart() {
        val listener = object : WifiP2pManager.ActionListener {
            override fun onFailure(reason: Int) {
//                startFailure(formatReason(R.string.repeater_create_group_failure, reason))
            }
            override fun onSuccess() { }    // wait for WIFI_P2P_CONNECTION_CHANGED_ACTION to fire to go to step 3
        }
        p2pManager.createGroup(channel, listener)
    }

    override fun onChannelDisconnected() {
        TODO("Not yet implemented")
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        TODO("Not yet implemented")
    }
}
