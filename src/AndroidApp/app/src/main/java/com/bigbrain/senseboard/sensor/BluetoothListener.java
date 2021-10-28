package com.bigbrain.senseboard.sensor;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.bigbrain.senseboard.MainActivity;
import com.bigbrain.senseboard.Permissions;

public class BluetoothListener extends Thread {
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final MainActivity context;
    private final BroadcastReceiver receiver;
    private final int pollingDelay;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BluetoothListener(MainActivity context, int pollingDelay) {
        this.context = context;
        this.pollingDelay = pollingDelay;
        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                    String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    System.out.println(name + ": " + device.getAddress() + " -> " + rssi);
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

    }

    public void discover() {
        bluetoothAdapter.startDiscovery();
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    @Override
    public void run() {
        discover();

        while (true) {
            long time = System.currentTimeMillis();

            discover();

            try {
                sleep(this.pollingDelay - Math.min((System.currentTimeMillis() - time), this.pollingDelay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void destroy() {
        context.unregisterReceiver(receiver);
    }


}
