package com.example.d039consolelauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onRedirect();
    }
    public String onRedirect() {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2333);
            return "";
        }*/
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("", null, "", null, null);
            return "";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}