package com.example.d029cameeaviewdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CameraView extends FrameLayout {
    public CameraView(@NonNull Context context) {
        super(context);
    }

    public void setAudio(@NonNull MediaStore.Audio audio) {

//        if (checkPermissions(audio)) {
            // Camera is running. Pass.
//            mCameraEngine.setAudio(audio);
            Camera.open();
//        }

    }

    public void open() {

//        if (checkPermissions(null)) {
            // Update display orientation for current CameraEngine
            Camera.open();
//        }
    }

    protected boolean checkPermissions(@NonNull MediaStore.Audio audio) {
        // Manifest is OK at this point. Let's check runtime permissions.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        Context c = getContext();
        boolean needsCamera = true;

        needsCamera = needsCamera && c.checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;

        if (!needsCamera ) {
            return true;
        } else  {
            requestPermissions(needsCamera);
            return false;
        }
    }
    private void requestPermissions(boolean requestCamera) {
        Activity activity = null;
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        List<String> permissions = new ArrayList<>();
        if (requestCamera) permissions.add(Manifest.permission.CAMERA);
        if (activity != null) {
            activity.requestPermissions(permissions.toArray(new String[0]),
                    233);
        }
    }
}
