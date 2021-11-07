package com.bigbrain.senseboard.sensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bigbrain.senseboard.MainActivity;
import com.bigbrain.senseboard.NearbyDevice;
import com.bigbrain.senseboard.R;
import com.bigbrain.senseboard.util.ApiService;

import java.util.HashMap;
import java.util.Map;

public class BluetoothListener extends Thread {
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final MainActivity context;
    private final BroadcastReceiver receiver;
    private final int pollingDelay;
    private final Map<String, NearbyDevice> devices = new HashMap<>();

    private final ApiService apiService;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BluetoothListener(MainActivity context, int pollingDelay, ApiService apiService) {
        this.context = context;
        this.pollingDelay = pollingDelay;
        this.apiService = apiService;

        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {

                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    System.out.println(intent.getStringExtra(BluetoothDevice.EXTRA_NAME) + " : " + device.getAddress());
                    if (!devices.containsKey(device.getAddress())) {
                        devices.put(device.getAddress(), NearbyDevice.fromBluetoothIntent(intent));
                    }
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }

    /**
     * Run one discovery cycle and cancel discovery afterwards
     */
    public void discover() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();

    }

    @Override
    public void run() {

        while (true) {
            long time = System.currentTimeMillis();

            System.out.println("List " + devices);
            System.out.println("MAC " + getMAC());

            apiService.updateNearbyDevices(devices.values());
            devices.clear();

            discover();


            try {
                sleep(this.pollingDelay - Math.min((System.currentTimeMillis() - time), this.pollingDelay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public String getMAC() {
        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.addressMAC), null);
    }

    /**
     * Retrieve the list of devices found in the most recent discovery cycle
     *
     * @return list of BluetoothDevices found in last discovery cycle
     */
    public HashMap<String, NearbyDevice> getDevices() {
        return new HashMap<>(this.devices);
    }

    /**
     * Unregister the BroadcastReceiver
     */
    public void unregister() {
        context.unregisterReceiver(receiver);
    }


}
