package com.example.lawnchairdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WallpaperManagerCompat wallpaperManagerCompat = new WallpaperManagerCompat(this);
//        wallpaperManagerCompat
    }
}