package com.bigbrain.senseboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bigbrain.senseboard.sensor.AudioListener;
import com.bigbrain.senseboard.sensor.SensorTracker;
import com.bigbrain.senseboard.util.FileUtil;
import com.bigbrain.senseboard.util.SensorSwitchHandler;
import com.bigbrain.senseboard.weka.ClassifiedActivity;

import org.apache.commons.lang3.RandomStringUtils;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_RECORD_AUDIO = 98;
    private final String apiCode;

    private SensorTracker st;
    private AudioListener al;
    private FileUtil fi;


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

        // Set up and start sensor tracker with given sensors

        st = new SensorTracker(this, 20,
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


        // Set up audio listener

        al = new AudioListener(this);

        al.start();

        // Set up file utility

        fi = new FileUtil(this);

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