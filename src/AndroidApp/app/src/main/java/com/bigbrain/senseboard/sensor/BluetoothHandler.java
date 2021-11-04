package com.bigbrain.senseboard.sensor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import com.bigbrain.senseboard.R;
import com.bigbrain.senseboard.util.ApiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BluetoothHandler {
    private BluetoothListener bl;
    private Context context;

    public BluetoothHandler(Context context) {
        this.bl = bl;
    }

    public void putBluetoothData() {
        JSONArray jarray = new JSONArray();
        for (Intent intent : bl.getIntents()) {
            JSONObject deviceJSON = new JSONObject();
            try {
                deviceJSON.put("hardwareAddress", intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
                deviceJSON.put("rssi", intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                deviceJSON.put("name", intent.getStringExtra(BluetoothDevice.EXTRA_NAME));

                jarray.put(deviceJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ApiUtil.putArrayData(context, jarray, context.getString(R.string.putActivityURL));
    }


}
