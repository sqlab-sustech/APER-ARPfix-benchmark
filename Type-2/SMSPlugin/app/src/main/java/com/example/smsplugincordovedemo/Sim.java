// MCC and MNC codes on Wikipedia
// http://en.wikipedia.org/wiki/Mobile_country_code

// Mobile Network Codes (MNC) for the international identification plan for public networks and subscriptions
// http://www.itu.int/pub/T-SP-E.212B-2014

// class TelephonyManager
// http://developer.android.com/reference/android/telephony/TelephonyManager.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/TelephonyManager.java

// permissions
// http://developer.android.com/training/permissions/requesting.html

// Multiple SIM Card Support
// https://developer.android.com/about/versions/android-5.1.html

// class SubscriptionManager
// https://developer.android.com/reference/android/telephony/SubscriptionManager.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/SubscriptionManager.java

// class SubscriptionInfo
// https://developer.android.com/reference/android/telephony/SubscriptionInfo.html
// https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/SubscriptionInfo.java

// Cordova Permissions API
// https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html#android-permissions

package com.example.smsplugincordovedemo;
/*

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;
*/

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.List;

public class Sim {
    private static final String LOG_TAG = "CordovaPluginSim";


    private static final String GET_SIM_INFO = "getSimInfo";
    private static final String HAS_READ_PERMISSION = "hasReadPermission";
    private static final String REQUEST_READ_PERMISSION = "requestReadPermission";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public boolean execute(Context context) throws JSONException {


        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // dual SIM detection with SubscriptionManager API
        // requires API 22
        // requires permission READ_PHONE_STATE
        JSONArray sims = null;
        Integer phoneCount = null;
        Integer activeSubscriptionInfoCount = null;
        Integer activeSubscriptionInfoCountMax = null;

        try {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            if (simPermissionGranted(Manifest.permission.READ_PHONE_STATE)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                }
                if (simPermissionGranted(Manifest.permission.READ_PHONE_STATE)) {
                    manager.getLine1Number();
                }
            }
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean simPermissionGranted(String type) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        ContextCompat contextCompat = null;
        return contextCompat.checkSelfPermission((Context) null, type) == PackageManager.PERMISSION_GRANTED;
    }
}
