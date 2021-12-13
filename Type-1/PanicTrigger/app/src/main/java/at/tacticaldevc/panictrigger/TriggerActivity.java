package at.tacticaldevc.panictrigger;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import at.tacticaldevc.panictrigger.utils.Utils;

public class TriggerActivity extends AppCompatActivity implements View.OnClickListener, LocationListener
{
    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger);

        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        String[] perms;
//        if((perms = Utils.checkPermissions(this)).length > 0)
//            requestPermissions(perms, 255);
    }

    @Override
    public void onClick(View v) {
      getCurrentLocationAndPanic();
    }


    private void getCurrentLocationAndPanic()
    {
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try
        {
            if(locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            else if(locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
            else
                locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        catch (Exception e)
        {
        }
    }


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(!Utils.onRequestPermissionsResult(requestCode, permissions, grantResults))
        {
        }
    }
}
