package com.bigbrain.senseboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.bigbrain.senseboard.sensor.AudioListener;
import com.bigbrain.senseboard.sensor.SensorTracker;
import com.bigbrain.senseboard.util.SensorSwitchHandler;
import com.bigbrain.senseboard.weka.ClassifiedActivity;
import com.bigbrain.senseboard.weka.ClassifyActivity;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_RECORD_AUDIO = 98;

    private final String apiCode;

    private SensorTracker st;
    private AudioListener al;

    //for classifier
    private static InputStream str;

    public MainActivity() {
        apiCode = RandomStringUtils.random(6, false, true);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create and display pairing code

        TextView pairingCode = findViewById(R.id.pairingCode);
        pairingCode.setText(apiCode);

        // Set up audio listener

        al = new AudioListener(this);

        al.startAudioRec();

        // Set up and start sensor tracker with given sensors

        st = new SensorTracker(this, al, 20,
                SensorManager.SENSOR_DELAY_FASTEST,   // Delay for all sensors
                Sensor.TYPE_ACCELEROMETER,            // Sensor 0
                Sensor.TYPE_GYROSCOPE,                // Sensor 1
                Sensor.TYPE_MAGNETIC_FIELD);          // Sensor 2

        st.start();

        // Set up switches

        SwitchCompat rec_0 = findViewById(R.id.rec_0);
        SwitchCompat rec_1 = findViewById(R.id.rec_1);
        SwitchCompat rec_2 = findViewById(R.id.rec_2);
        SwitchCompat rec_3 = findViewById(R.id.rec_3);
        SwitchCompat rec_4 = findViewById(R.id.rec_4);
        SwitchCompat rec_5 = findViewById(R.id.rec_5);
        SwitchCompat[] switches = new SwitchCompat[]{rec_0, rec_1, rec_2, rec_3, rec_4, rec_5};
        SensorSwitchHandler ssh = new SensorSwitchHandler(this, switches);

        for (int i = 0; i < switches.length; i++) {
            String text = "Record " + ClassifiedActivity.getActivityName(i);
            switches[i].setText(text);
            int finalI = i;
            switches[i].setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    ssh.deactivateOthers(switches[finalI]);
                    st.startRecord(finalI); // Ensure that i is an activity in ClassifiedActivity
                } else {
                    st.stopRecord();
                }
            });
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case PERMISSIONS_RECORD_AUDIO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    al.start();
                } else {
                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                            , PERMISSIONS_RECORD_AUDIO);
                }
                break;

        }
    }

    //for classifier - not sure if it's the right way, or if it even works, but I can't use "getAssets"
    //in a class that isn't an Activity
    public void getTheAssets() {
        AssetManager assetManager = getAssets();
        try {
            str = assetManager.open("randomForestRightPocket.model");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static InputStream getStr() {
        return str;
    }
}