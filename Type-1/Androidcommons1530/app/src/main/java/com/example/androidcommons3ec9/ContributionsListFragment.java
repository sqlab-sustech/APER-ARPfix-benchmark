package com.example.androidcommons3ec9;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class ContributionsListFragment extends Fragment {
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //See http://stackoverflow.com/questions/33169455/onrequestpermissionsresult-not-being-called-in-dialog-fragment
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                return false;
            } else {*/
                Intent nearbyIntent = new Intent(getActivity(), NearbyActivity.class);
                startActivity(nearbyIntent);
//            }
//        }
        return true;
    }
}
