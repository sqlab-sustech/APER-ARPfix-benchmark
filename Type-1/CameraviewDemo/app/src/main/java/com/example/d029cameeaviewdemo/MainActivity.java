package com.example.d029cameeaviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CameraView cameraView = new CameraView(null);
        cameraView.open();
        cameraView.setAudio(null);
    }
}