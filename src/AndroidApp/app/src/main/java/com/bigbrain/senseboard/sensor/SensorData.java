package com.bigbrain.senseboard.sensor;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

public class SensorData {
    private List<JSONArray> data;

    public SensorData () {

    }

    public void addRow(float[] row) {
        data.add(new JSONArray(Arrays.asList(row)));
    }

    public JSONArray getJSON() {
        return new JSONArray(data);
    }
}
