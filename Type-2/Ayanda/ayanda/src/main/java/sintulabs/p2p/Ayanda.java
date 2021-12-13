package sintulabs.p2p;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sabzo on 1/19/18.
 */

public class Ayanda {
    private WifiDirect wd;

    private Context context;

    /**
     * Ayanda is a class that discovers and interacts with nearby devices that support
     * Network Service Discovery (NSD), WiFi Direct, and  Bluetooth
     * @param context The Activity/Application Context
     * @param iBluetooth An interface to handle Bluetooth events
     * @param iLan An interface to handle LAN (NSD/Bonjour/ZeroConfig/etc.,) events
     * @param iWifiDirect An interface to handle Wifi Direct events
     */
    public Ayanda(Context context, IBluetooth iBluetooth, ILan iLan, IWifiDirect iWifiDirect) {
        this.context = context;
            wd = new WifiDirect(context, iWifiDirect);
    }


    /* Wifi Direct Methods */

    /**
     *
     * @param device to send data to
     * @param bytes array of data to send
     */
    public void wdSendData(WifiP2pDevice device, byte[] bytes) {
        wd.sendData(device, bytes);
    }

    public void wdShareFile () throws IOException {
        wd.shareFile();
    }


    /**
     * Connect to a WifiDirect device
     * @param device
     */
    public void wdConnect(WifiP2pDevice device) {
        wd.connect(device);
    }

    /**
     * Discover nearby WiFi Direct enabled devices
     */
    public void wdDiscover() {
        wd.discover();
    }

    public void wdRegisterReceivers() {
        wd.registerReceivers();
    }

    public void wdUnregisterReceivers() {
        wd.unregisterReceivers();
    }

    public ArrayList<WifiP2pDevice> wdGetDevicesDiscovered() {
        return wd.getDevicesDiscovered();
    }

}
