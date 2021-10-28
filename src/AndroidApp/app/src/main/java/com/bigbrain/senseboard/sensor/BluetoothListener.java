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

public class BluetoothListener {
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BluetoothListener(MainActivity context) {
        context.registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {

            context.requestPermissions(new String[]{Manifest.permission.BLUETOOTH}
                    , Permissions.PERMISSION_BLUETOOTH);
        }
    }

    public void startDiscovery() {
        bluetoothAdapter.startDiscovery();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                System.out.println(name + " -> " + rssi);
            }
        }
    };

}
