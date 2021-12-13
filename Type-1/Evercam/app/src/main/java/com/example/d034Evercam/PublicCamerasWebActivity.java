package com.example.d034Evercam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PublicCamerasWebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*if(!Permission.isGranted(this, Permission.LOCATION))
        {
            Permission.request(this, new String[]{Permission.LOCATION}, Permission.REQUEST_CODE_LOCATION);
        }
        else
        {*/
            loadPage();
//        }
        super.onCreate(savedInstanceState);
    }

    protected void loadPage()
    {
        PublicCamerasWebView webView = (PublicCamerasWebView) findViewById(R.id.accelerate);
        webView.loadPublicCameras();
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        switch(requestCode)
        {
            case Permission.REQUEST_CODE_LOCATION:
                loadPage();
                break;
        }
    }*/
}
