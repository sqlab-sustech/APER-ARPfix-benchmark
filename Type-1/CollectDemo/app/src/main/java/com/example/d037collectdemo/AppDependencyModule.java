package com.example.d037collectdemo;

import android.content.Context;
import android.telephony.TelephonyManager;

public class AppDependencyModule {
    public String getLine1Number(Context context) {
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telMgr.getLine1Number();
    }
}
