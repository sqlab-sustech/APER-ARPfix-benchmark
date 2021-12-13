package com.wyb.androidclientdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class RegistrationService {
    public static void getMyNumber(Context context) {
//        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String regionCode = tm.getSimCountryIso().toUpperCase(Locale.US);
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
            {
                if(ContextCompat.checkSelfPermission(context,"android.Manifest.permission.READ_PHONE_STATE")!= PackageManager.PERMISSION_GRANTED)
                {
                    //request
                }
            }
            else
            {
                if(ContextCompat.checkSelfPermission(context,"android.Manifest.permission.READ_PHONE_NUMBERS")!= PackageManager.PERMISSION_GRANTED)
                {
                    //request
                }
            }
            tm.getLine1Number();
            return;
        }
/*        catch (Exception e) {
            return ;
        }*/
    }
}
