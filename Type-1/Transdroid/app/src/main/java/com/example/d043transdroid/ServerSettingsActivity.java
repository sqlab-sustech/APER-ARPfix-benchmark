package com.example.d043transdroid;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ServerSettingsActivity extends AppCompatActivity {
    private EditTextPreference localNetworkPreference;
    protected ConnectivityHelper connectivityHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityHelper = new ConnectivityHelper();
        //finally method
        //return wifiManager.getConnectionInfo().getSSID().replace("\"", "");
//        if (!connectivityHelper.hasNetworkNamePermission(ServerSettingsActivity.this)) {
//            connectivityHelper.askNetworkNamePermission(ServerSettingsActivity.this);
            connectivityHelper.getConnectedNetworkName();
//        }
        return;
       /* return false;
        localNetworkPreference = initTextPreference("server_localnetwork");
        localNetworkPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                if (!connectivityHelper.hasNetworkNamePermission(ServerSettingsActivity.this)) {
                    connectivityHelper.askNetworkNamePermission(ServerSettingsActivity.this);
                    return true;
                }
                return false;
            }
        });*/
    }
/*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (connectivityHelper.requestedPermissionWasGranted(requestCode, permissions, grantResults)) {
            connectivityHelper.askNetworkNamePermission(this);
//            localNetworkPreference.getOnPreferenceClickListener().onPreferenceClick(localNetworkPreference);
        }
    }
*/



}
