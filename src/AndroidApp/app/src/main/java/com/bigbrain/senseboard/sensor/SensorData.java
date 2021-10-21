package com.bigbrain.senseboard.sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

public class SensorData {
    private List<float[]> data;

    public SensorData () {
        data = new ArrayList<>();
    }

    public void addRow(float[] row) {
//        data.add(new JSONArray(Arrays.asList(row)));
        data.add(row);
    }

    public JSONArray getJSON() {
        return new JSONArray(data);
    }
}
