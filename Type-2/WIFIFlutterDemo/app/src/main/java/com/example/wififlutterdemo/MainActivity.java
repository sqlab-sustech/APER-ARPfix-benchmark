package com.example.wififlutterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WifiInfoPlugin wifiInfoPlugin = new WifiInfoPlugin();
        wifiInfoPlugin.onMethodCall();
    }
}