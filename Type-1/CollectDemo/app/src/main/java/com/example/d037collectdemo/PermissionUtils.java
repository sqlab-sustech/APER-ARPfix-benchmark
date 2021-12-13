package com.example.d037collectdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class PermissionUtils {
    public boolean isReadPhoneStatePermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED;
    }
    public void requestLocationPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_PHONE_STATE},233);
    }
  /*  private final int dialogTheme;

    public PermissionUtils(int dialogTheme) {
        this.dialogTheme = dialogTheme;
    }

    public static boolean areStoragePermissionsGranted(Context context) {
        return isPermissionGranted(context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean isCameraPermissionGranted(Context context) {
        return isPermissionGranted(context, Manifest.permission.CAMERA);
    }

    public static boolean areLocationPermissionsGranted(Context context) {
        return isPermissionGranted(context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean areCameraAndRecordAudioPermissionsGranted(Context context) {
        return isPermissionGranted(context,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO);
    }

    public static boolean isGetAccountsPermissionGranted(Context context) {
        return isPermissionGranted(context, Manifest.permission.GET_ACCOUNTS);
    }

    public boolean isReadPhoneStatePermissionGranted(Context context) {
        return isPermissionGranted(context, Manifest.permission.READ_PHONE_STATE);
    }

    *//**
     * Returns true only if all of the requested permissions are granted to Collect, otherwise false
     *//*
    private static boolean isPermissionGranted(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void finishAllActivities(Activity activity) {
        activity.finishAndRemoveTask();
    }

    *//**
     * Checks to see if the user granted Collect the permissions necessary for reading
     * and writing to storage and if not utilizes the permissions API to request them.
     *
     * @param activity needed for requesting permissions
     * @param action is a listener that provides the calling component with the permission result.
     *//*
    public void requestStoragePermissions(Activity activity, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                showAdditionalExplanation(activity, R.string.storage_runtime_permission_denied_title,
                        R.string.storage_runtime_permission_denied_desc, R.drawable.sd, action);
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void requestCameraPermission(Activity activity, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                showAdditionalExplanation(activity, R.string.camera_runtime_permission_denied_title,
                        R.string.camera_runtime_permission_denied_desc, R.drawable.ic_photo_camera, action);
            }
        }, Manifest.permission.CAMERA);
    }

    public void requestLocationPermissions(Activity activity, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                showAdditionalExplanation(activity, R.string.location_runtime_permissions_denied_title,
                        R.string.location_runtime_permissions_denied_desc, R.drawable.ic_room_black_24dp, action);
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public void requestRecordAudioPermission(Activity activity, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                showAdditionalExplanation(activity, R.string.record_audio_runtime_permission_denied_title,
                        R.string.record_audio_runtime_permission_denied_desc, R.drawable.ic_mic, action);
            }
        }, Manifest.permission.RECORD_AUDIO);
    }

    public void requestCameraAndRecordAudioPermissions(Activity activity, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                showAdditionalExplanation(activity, R.string.camera_runtime_permission_denied_title,
                        R.string.camera_runtime_permission_denied_desc, R.drawable.ic_photo_camera, action);
            }
        }, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);
    }

    public void requestGetAccountsPermission(Activity activity, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                showAdditionalExplanation(activity, R.string.get_accounts_runtime_permission_denied_title,
                        R.string.get_accounts_runtime_permission_denied_desc, R.drawable.ic_get_accounts, action);
            }
        }, Manifest.permission.GET_ACCOUNTS);
    }

    public void requestReadPhoneStatePermission(Activity activity, boolean displayPermissionDeniedDialog, @NonNull PermissionListener action) {
        requestPermissions(activity, new PermissionListener() {
            @Override
            public void granted() {
                action.granted();
            }

            @Override
            public void denied() {
                if (displayPermissionDeniedDialog) {
                    showAdditionalExplanation(activity, R.string.read_phone_state_runtime_permission_denied_title,
                            R.string.read_phone_state_runtime_permission_denied_desc, R.drawable.ic_phone, action);
                } else {
                    action.denied();
                }
            }
        }, Manifest.permission.READ_PHONE_STATE);
    }

    protected void requestPermissions(Activity activity, @NonNull PermissionListener listener, String... permissions) {
        DexterBuilder builder = null;

        if (permissions.length == 1) {
            builder = createSinglePermissionRequest(activity, permissions[0], listener);
        } else if (permissions.length > 1) {
            builder = createMultiplePermissionsRequest(activity, listener, permissions);
        }

        if (builder != null) {
            builder.withErrorListener(error -> Timber.i(error.name())).check();
        }
    }

    private DexterBuilder createSinglePermissionRequest(Activity activity, String permission, PermissionListener listener) {
        return Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(new com.karumi.dexter.listener.single.PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        listener.granted();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        listener.denied();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                });
    }

    private DexterBuilder createMultiplePermissionsRequest(Activity activity, PermissionListener listener, String[] permissions) {
        return Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            listener.granted();
                        } else {
                            listener.denied();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                });
    }

    protected void showAdditionalExplanation(Activity activity, int title, int message, int drawable, @NonNull PermissionListener action) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, dialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> action.denied())
                .setCancelable(false)
                .setIcon(drawable)
                .create();

        DialogUtils.showDialog(alertDialog, activity);
    }*/
}
