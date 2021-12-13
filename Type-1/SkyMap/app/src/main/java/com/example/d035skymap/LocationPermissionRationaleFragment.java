package com.example.d035skymap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


/**
 * Dialog explaining the need for the auto-location permission.
 * Created by johntaylor on 4/3/16.
 */
public class LocationPermissionRationaleFragment extends DialogFragment implements Dialog.OnClickListener {
    private Callback resultListener;

    public interface Callback {
        void done();
    }

    public LocationPermissionRationaleFragment() {
    }

    public void setCallback(Callback resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onClick(DialogInterface ignore1, int ignore2) {
        if (resultListener != null) {
            resultListener.done();
        }
    }
}