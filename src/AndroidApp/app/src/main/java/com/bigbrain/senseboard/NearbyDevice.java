package com.bigbrain.senseboard;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

public class NearbyDevice {

    public NearbyDevice(String hardwareAddress, short rssi){
        this.hardwareAddress = hardwareAddress;
        this.rssi = rssi;
    }

    public NearbyDevice(String hardwareAddress, short rssi, String deviceName){
        this(hardwareAddress, rssi);
        this.name = deviceName;
    }

    private String hardwareAddress;
    private short rssi;

    /**
     * Just for debugging purposes really
     */
    private String name;

    public double getRssi() {
        return rssi;
    }

    public void setRssi(short rssi) {
        this.rssi = rssi;
    }

    public String getHardwareAddress() {
        return hardwareAddress;
    }

    public void setHardwareAddress(String hardwareAddress) {
        this.hardwareAddress = hardwareAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static NearbyDevice fromBluetoothIntent(Intent intent){
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
        short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
        return new NearbyDevice(device.getAddress(), rssi, name);
    }


}
