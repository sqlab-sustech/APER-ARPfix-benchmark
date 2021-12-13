package com.example.d034Evercam;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PublicCamerasWebView extends WebView {
    private final String TAG = "PublicCamerasWebView";

    public PublicCamerasWebView(Context context) {
        super(context);
    }



    public void loadPublicCameras() {

        enableGeoLocation();
    }
    protected void enableGeoLocation() {
        WebSettings webSettings = this.getSettings();
        webSettings.setGeolocationEnabled(true);
//        getSettings().setGeolocationDatabasePath(getContext().getFilesDir().getPath());

    }
}
