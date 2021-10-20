package com.bigbrain.senseboard.sensor;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

/**
 * Sets up a given set of sensors and polls their readings at a given frequency
 */
public class SensorTracker extends Thread {
    private SensorHandler[] sensorHandlers;
    private final int pollingDelay;
    private SensorData sensorData;

    /**
     * Constructor for the SensorHandler class
     * @param context Sensor context
     * @param delay delay for sensor, e.g. SensorManager.SENSOR_DELAY_FASTEST
     * @param pollingDelay polling rate of this class on all sensors
     * @param sensorTypes array of sensorTypes, e.g. [SensorManager.TYPE_ACCELEROMETER]
     */
    public SensorTracker(Context context, int pollingDelay, int delay, int pollingRate1, int... sensorTypes) {
        this.pollingDelay = pollingDelay;
        sensorHandlers = new SensorHandler[sensorTypes.length];
        for (int i = 0; i < sensorHandlers.length; i++) {
            sensorHandlers[i] = new SensorHandler(context, sensorTypes[i], delay);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        while (true) {
            long time = System.currentTimeMillis();

            if (Arrays.stream(sensorHandlers).allMatch(x -> x.getLastSensorEvent() != null)) {
                float[] res = new float[0];
                for (SensorHandler sensorHandler : sensorHandlers) {
                    res = concatFloatArrays(res, sensorHandler.getLastSensorEvent().values);
                }
                this.sensorData.addRow(res);
            }

            try {
                sleep(20 - time % 20);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Concatenate two float arrays
     * @param a first array
     * @param b second array
     * @return concatenation of the two arrays
     */
    private float[] concatFloatArrays(float[] a, float[] b) {
        float[] res = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }
}
