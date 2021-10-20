package com.bigbrain.senseboard.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorHandler implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEvent lastSensorEvent;

    /**
     * Constructor for the SensorHandler class
     * @param context Sensor context
     * @param type type of sensor, e.g. SensorManager.TYPE_ACCELEROMETER
     * @param delay delay for sensor, e.g. SensorManager.SENSOR_DELAY_FASTEST
     */
    public SensorHandler(Context context, int type, int delay) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(type);
        sensorManager.registerListener(this, sensor, delay);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.lastSensorEvent = sensorEvent;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Do nothing
    }

    public SensorEvent getLastSensorEvent() {
        return this.lastSensorEvent;
    }
}
