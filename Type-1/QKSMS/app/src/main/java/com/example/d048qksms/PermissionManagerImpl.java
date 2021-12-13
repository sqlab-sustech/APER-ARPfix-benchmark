package com.example.d048qksms;

import android.Manifest;
import android.content.Context;

import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class PermissionManagerImpl implements PermissionManager {
    Context context;
    public PermissionManagerImpl(Context context)
    {
        this.context = context;
    }

    @Override
    public Boolean hasPhone() {
        return true;
//        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PERMISSION_GRANTED;
    }
}
