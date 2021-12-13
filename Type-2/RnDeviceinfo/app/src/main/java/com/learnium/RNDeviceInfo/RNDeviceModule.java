package com.learnium.RNDeviceInfo;

import android.content.Context;
import android.telephony.TelephonyManager;
import androidx.appcompat.app.AppCompatActivity;


//@ReactModule(name = RNDeviceModule.NAME)
public class RNDeviceModule extends AppCompatActivity {

  @Override
  protected void onStart() {
    super.onStart();
    getPhoneNumberSync();
  }

  //  @SuppressLint({"HardwareIds", "MissingPermission"})
//  @ReactMethod(isBlockingSynchronousMethod = true)
  public String getPhoneNumberSync() {
    /*if (getReactApplicationContext() != null &&
            (getReactApplicationContext().checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getReactApplicationContext().checkCallingOrSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && getReactApplicationContext().checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED))) {
*/      TelephonyManager telMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//      if (telMgr != null) {
        return telMgr.getLine1Number();
//      } else {
//        System.err.println("Unable to getPhoneNumber. TelephonyManager was null");
//      }
////    }
//    return "unknown";
  }/*
  @ReactMethod
  public void getPhoneNumber(Promise p) { p.resolve(getPhoneNumberSync()); }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public WritableArray getSupportedAbisSync() {
    WritableArray array = new WritableNativeArray();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      for (String abi : Build.SUPPORTED_ABIS) {
        array.pushString(abi);
      }
    } else {
      array.pushString(Build.CPU_ABI);
    }
    return array;
  }
  @ReactMethod
  public void getSupportedAbis(Promise p) { p.resolve(getSupportedAbisSync()); }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public WritableArray getSupported32BitAbisSync() {
    WritableArray array = new WritableNativeArray();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      for (String abi : Build.SUPPORTED_32_BIT_ABIS) {
        array.pushString(abi);
      }
    }
    return array;
  }
  @ReactMethod
  public void getSupported32BitAbis(Promise p) { p.resolve(getSupported32BitAbisSync()); }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public WritableArray getSupported64BitAbisSync() {
    WritableArray array = new WritableNativeArray();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      for (String abi : Build.SUPPORTED_64_BIT_ABIS) {
        array.pushString(abi);
      }
    }
    return array;
  }
  @ReactMethod
  public void getSupported64BitAbis(Promise p) { p.resolve(getSupported64BitAbisSync()); }


  private WritableMap getPowerStateFromIntent (Intent intent) {
    if(intent == null) {
      return null;
    }

    int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    int isPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

    float batteryPercentage = batteryLevel / (float)batteryScale;

    String batteryState = "unknown";

    if(isPlugged == 0) {
      batteryState = "unplugged";
    } else if(status == BATTERY_STATUS_CHARGING) {
      batteryState = "charging";
    } else if(status == BATTERY_STATUS_FULL) {
      batteryState = "full";
    }

    PowerManager powerManager = (PowerManager)getReactApplicationContext().getSystemService(Context.POWER_SERVICE);
    boolean powerSaveMode = false;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      powerSaveMode = powerManager.isPowerSaveMode();
    }

    WritableMap powerState = Arguments.createMap();
    powerState.putString(BATTERY_STATE, batteryState);
    powerState.putDouble(BATTERY_LEVEL, batteryPercentage);
    powerState.putBoolean(LOW_POWER_MODE, powerSaveMode);

    return powerState;
  }

  private void sendEvent(ReactContext reactContext,
                         String eventName,
                         @Nullable Object data) {
    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, data);
  }*/
}
