package com.wyb.robolectricdemo;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.O;
import static android.os.Build.VERSION_CODES.P;
import static android.os.Build.VERSION_CODES.Q;
import static android.telephony.PhoneStateListener.LISTEN_CALL_STATE;
import static android.telephony.PhoneStateListener.LISTEN_CELL_INFO;
import static android.telephony.PhoneStateListener.LISTEN_CELL_LOCATION;
import static android.telephony.PhoneStateListener.LISTEN_NONE;
import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.telecom.PhoneAccountHandle;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyManager.CellInfoCallback;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class ShadowTelephonyManager {

  protected TelephonyManager realTelephonyManager;
  private TelephonyManager telephonyManager;
  public void shouldGiveCellInfoUpdate(){
//    assertNotEquals(callbackCellInfo, telephonyManager.getAllCellInfo());
    Object o = new Object();
//    CountDownLatch callbackLatch = new CountDownLatch(1);

    checkPermission();
//    if (VERSION.SDK_INT >= Q && ContextCompat.checkSelfPermission((Context)null, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
      requestCellInfoUpdate(o, o);
//    }
  }
  /**
   * Returns the value set by {@link #}, defaulting to calling the real {@link
   * TelephonyManager#NETWORK_TYPE_UNKNOWN} if it was never called.
   */
  protected void requestCellInfoUpdate(Object cellInfoExecutor, Object cellInfoCallback) {
    Executor executor = (Executor) cellInfoExecutor;
    realTelephonyManager.requestCellInfoUpdate(executor, (CellInfoCallback) cellInfoCallback);
  }
  private void checkPermission()
  {

  }
}
