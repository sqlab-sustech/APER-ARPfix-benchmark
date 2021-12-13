package com.example.d032weechatandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

public class PreferencesActivity extends AppCompatActivity {
    final static private String KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        PreferencesFragment p = new PreferencesFragment();
        p.onDisplayPreferenceDialog(null);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static class PreferencesFragment extends Fragment {

        private static final String FRAGMENT_DIALOG_TAG = "android.support.v7.preference.PreferenceFragment.DIALOG";

        public void onDisplayPreferenceDialog(Preference preference) {
/*            boolean granted = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            if (!granted) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                return;
            }*/
            String SEARCH_DIR = Environment.getExternalStorageDirectory().toString() + "/weechat";
// load themes from disk
            File dir = new File(SEARCH_DIR);
            File[] files = dir.listFiles();

        }
    }
}