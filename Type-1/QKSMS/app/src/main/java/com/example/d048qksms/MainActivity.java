package com.example.d048qksms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManagerImpl permissionManager = new PermissionManagerImpl(this);
        SubscriptionUtilsImpl subscriptionUtils = new SubscriptionUtilsImpl(this,permissionManager);
        subscriptionUtils.getSubscriptions();
    }
}