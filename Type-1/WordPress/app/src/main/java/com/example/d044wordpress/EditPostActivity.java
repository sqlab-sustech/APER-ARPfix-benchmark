package com.example.d044wordpress;

import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class EditPostActivity extends ActivityCompat implements ActivityCompat.OnRequestPermissionsResultCallback {

    private View mMenuView = null;
    private EditPostSettingsFragment mEditPostSettingsFragment;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                boolean shouldShowLocation = false;
                // Check if at least one of the location permission (coarse or fine) is granted
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        shouldShowLocation = true;
                    }
                }
                if (shouldShowLocation) {
                    // Permission request was granted, show Location buttons in Settings
                    mEditPostSettingsFragment.showLocationSearch();
                    return;
                }
                break;
            default:
                break;
        }
    }
}
