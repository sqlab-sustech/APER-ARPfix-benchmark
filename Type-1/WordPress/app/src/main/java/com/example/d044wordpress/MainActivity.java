package com.example.d044wordpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //直接调用fragment
        EditPostSettingsFragment editPostSettingsFragment = new EditPostSettingsFragment();
        FragmentManager FM = getSupportFragmentManager();
        FM.beginTransaction().add(editPostSettingsFragment,"").commit();
//        FM.commit();
    }
}