package com.example.d022omim;

import android.app.Activity;
import android.content.Context;

public class LocationHealper {
    private Context mContext;
    private BaseLocationProvider mLocationProvider;
    public void startInternal()
    {

       /* if (!PermissionUtils.isLocationGranted(mContext))
        {
            PermissionUtils.requestLocationPermission((Activity) mContext,233);
            return;
        }*/
        //noinspection ConstantConditions
        mLocationProvider.start();
    }
}
