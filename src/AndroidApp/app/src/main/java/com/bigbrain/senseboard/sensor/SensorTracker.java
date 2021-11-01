package com.bigbrain.senseboard.sensor;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bigbrain.senseboard.MainActivity;
import com.bigbrain.senseboard.util.FileUtil;
import com.bigbrain.senseboard.weka.Activities;
import com.bigbrain.senseboard.weka.ClassifyActivity;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Sets up a given set of sensors and polls their readings at a given frequency
 */
public class SensorTracker extends Thread {
    private SensorHandler[] sensorHandlers;
    private final int pollingDelay;
    private SensorData sensorData;
    private Context context;
    private FileUtil fu;
    private AudioListener audioListener;
    private ClassifyActivity cla;

    private AtomicBoolean record = new AtomicBoolean(false);



    /**
     * Constructor for the SensorHandler class
     * @param context Sensor context
     * @param delay delay for sensor, e.g. SensorManager.SENSOR_DELAY_FASTEST
     * @param pollingDelay polling rate of this class on all sensors
     * @param sensorTypes array of sensorTypes, e.g. Sensor.TYPE_ACCELEROMETER
     */
    public SensorTracker(Context context, AudioListener al, int pollingDelay, int delay, int... sensorTypes) {
        this.context = context;
        this.audioListener = al;
        this.pollingDelay = pollingDelay;
        sensorHandlers = new SensorHandler[sensorTypes.length];
        for (int i = 0; i < sensorHandlers.length; i++) {
            sensorHandlers[i] = new SensorHandler(context, sensorTypes[i], delay);
        }
        sensorData = new SensorData();
        cla = new ClassifyActivity((MainActivity) context);
    }


    /**
     * Start recording sensor data to a file in the
     */
    public void stopRecord () {
        this.record.set(false);
        if (fu != null) {
            fu.close();
        }
    }



    public void startRecord(Activities activity) {
        fu = new FileUtil(this.context);
        fu.setFileName("Activity_" + activity.label + ".csv");
        fu.setup();
        this.record.set(true);
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
//                res = concatFloatArrays(res, new float[]{audioListener.getAverageVolume()});
                this.sensorData.addRow(res);
                cla.putValues(res);

                if (record.get()) {
//                    System.out.println("Recording to " + context.getFilesDir() + "/" + fu.getFileName());
                    fu.writeData(res);
                }
            }

            try {
                sleep(this.pollingDelay - Math.min((System.currentTimeMillis() - time), this.pollingDelay));
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
