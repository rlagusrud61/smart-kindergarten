package com.bigbrain.senseboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.bigbrain.senseboard.sensor.AudioListener;
import com.bigbrain.senseboard.sensor.SensorTracker;

import org.apache.commons.lang3.RandomStringUtils;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_RECORD_AUDIO = 98;
    private final String apiCode;

    private SensorTracker st;
    private AudioListener al;


    public MainActivity() {
        apiCode = RandomStringUtils.random(6, false, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView pairingCode = findViewById(R.id.pairingCode);
        pairingCode.setText(apiCode);

        st = new SensorTracker(this, 20,
                SensorManager.SENSOR_DELAY_FASTEST,   // Delay for all sensors
                Sensor.TYPE_ACCELEROMETER,            // Sensor 0
                Sensor.TYPE_GYROSCOPE,                // Sensor 1
                Sensor.TYPE_MAGNETIC_FIELD);          // Sensor 2

        st.start();

        al = new AudioListener(this);

        al.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case PERMISSIONS_RECORD_AUDIO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    al.start();
                } else {
                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                            , PERMISSIONS_RECORD_AUDIO);
                }
                break;

        }
    }


}