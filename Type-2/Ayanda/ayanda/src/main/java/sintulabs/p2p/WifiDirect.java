package sintulabs.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import android.location.LocationManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


/**
 * WiFi Direct P2P Class for detecting and connecting to nearby devices
 */
public class WifiDirect extends P2P {
    private static WifiP2pManager wifiP2pManager;
    private static WifiP2pManager.Channel wifiDirectChannel;
    private Context context;
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private Boolean wiFiP2pEnabled = false;
    private Boolean isGroupOwner = false;
    private InetAddress groupOwnerAddress;
    private ArrayList <WifiP2pDevice> peers = new ArrayList();
    private IWifiDirect iWifiDirect;


    private int  serverPort = 8080;
    private Boolean isClient = false;
    private Boolean isServer = false;

    /**
     * Creates a WifiDirect instance
     * @param context activity/application contex
     * @param iWifiDirect an inteface to provide callbacks to WiFi Direct events
     */
    public WifiDirect(Context context, IWifiDirect iWifiDirect) {
        this.context = context;
        this.iWifiDirect = iWifiDirect;
        createReceiver();
    }



    /**
     * receiver for WiFi direct hardware events
     */
    private void createReceiver() {
        wifiP2pPeersChangedAction();
    }



    /**
     * When new peers are discovered
     */
    public void wifiP2pPeersChangedAction() {
            wifiP2pManager.requestPeers(wifiDirectChannel, new WifiP2pManager.PeerListListener() {
                @Override
                public void onPeersAvailable(WifiP2pDeviceList peerList) {
                    peers.clear();
                    peers.addAll(peerList.getDeviceList());
                }
            });
    }




    public void registerReceivers() {
        context.registerReceiver(receiver, intentFilter);
    }

    public void unregisterReceivers() {
        context.unregisterReceiver(receiver);
    }

    /**
     * look for nearby peers
     */

    private void discoverPeers() {

        wifiP2pManager.discoverPeers(wifiDirectChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reasonCode) {
                Log.d("Debug", "failed to look for pears: " + reasonCode);
            }
        });
    }

    /**
     * Return devices discovered. Method should be called when WIFI_P2P_PEERS_CHANGED_ACTION
     is complete
     * @return Arraylist <WifiP2pDevice>
     */
    public ArrayList<WifiP2pDevice> getDevicesDiscovered() {
        return peers;
    }

    /**
     * Connect to a nearby device
     * @param device
     */
    public void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        wifiP2pManager.connect(wifiDirectChannel,config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                // todo if failure == 2
            }
        });
    }

    /**f
     * Should be called when a connection has already been made to WifiP2pDevice
     * @param device
     * @param bytes
     */
    public void sendData(WifiP2pDevice device, byte[] bytes) {


    }



    public void shareFile() {

        discover();
    }
    /**
     * Android 8.0+ requires location to be turned on when discovering
     * nearby devices.
     * @return boolean
     */
    public boolean isLocationOn() {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }




    @Override
    public void announce() {}

    @Override
    public void discover() {
        discoverPeers();
    }

    /**
     * is Wifi Direct supported
     * @return
     */
    @Override
    public Boolean isSupported() {
        return null;
    }

    /**
     * is Wifi Direct enabled
     * @return
     */
    @Override
    public Boolean isEnabled() {
        return null;
    }
}