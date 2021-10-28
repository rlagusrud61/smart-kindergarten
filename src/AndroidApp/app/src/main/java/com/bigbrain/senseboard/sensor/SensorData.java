package com.bigbrain.senseboard.sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

/**
 * Class that passes data to realtime activity recognition
 */
public class SensorData {
    private List<float[]> data;

    public SensorData () {
        data = new ArrayList<>();
    }

    public void addRow(float[] row) {
        data.add(row);
        }

    public void storeData(float[] row, String fileName) {

    }

    public JSONArray getJSON() {
        return new JSONArray(data);
    }
}
