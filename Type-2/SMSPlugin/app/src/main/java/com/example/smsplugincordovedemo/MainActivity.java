package com.example.smsplugincordovedemo;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sim sim = new Sim();
        try {
            sim.execute(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}