package com.example.d028glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private QMediaStoreUriLoader qMediaStoreUriLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qMediaStoreUriLoader = new QMediaStoreUriLoader();
        //C&R
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            qMediaStoreUriLoader.buildDelegateData();
//        }
    }
}