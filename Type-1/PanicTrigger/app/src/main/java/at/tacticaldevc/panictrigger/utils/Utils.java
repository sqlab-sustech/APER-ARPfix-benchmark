package at.tacticaldevc.panictrigger.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.tacticaldevc.panictrigger.R;

public class Utils {



    //permission related stuff
    public static String[] checkPermissions(Context c)
    {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.RECEIVE_SMS);
        permissions.add(Manifest.permission.SEND_SMS);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.CALL_PHONE);
        permissions.add(Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> permissionsToRequest = new ArrayList<>();
        for(String perm : permissions)
        {
            if(c.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED)
                permissionsToRequest.add(perm);
        }

        return permissionsToRequest.toArray(new String[permissionsToRequest.size()]);
    }

    public static boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(requestCode == 255 && grantResults.length > 0)
        {
            for (int grantResult : grantResults)
            {
                if(grantResult != PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }
}
