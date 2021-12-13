package com.example.d028glide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.FileNotFoundException;
@RequiresApi(Build.VERSION_CODES.Q)
public class QMediaStoreUriLoader {
    @Nullable
    public void buildDelegateData()  {

            Uri toLoad = isAccessMediaLocationGranted() ? MediaStore.setRequireOriginal(null) : null;
        }
    Context context;
    private boolean isAccessMediaLocationGranted() {
        return context.checkSelfPermission(android.Manifest.permission.ACCESS_MEDIA_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}
