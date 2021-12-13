package com.example.d024mapboxevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapboxTelemetry mapboxTelemetry = new MapboxTelemetry();
        mapboxTelemetry.optLocationIn();
    }
}