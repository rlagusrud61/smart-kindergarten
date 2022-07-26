package com.bigbrain.senseboard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.bigbrain.senseboard.sensor.AudioListener;
import com.bigbrain.senseboard.sensor.BluetoothListener;
import com.bigbrain.senseboard.sensor.SensorTracker;
import com.bigbrain.senseboard.util.SensorSwitchHandler;
import com.bigbrain.senseboard.weka.Activities;

import org.apache.commons.lang3.RandomStringUtils;


public class MainActivity extends AppCompatActivity {

//    private static final int PERMISSIONS_RECORD_AUDIO = 98;
//    private static final int PERMISSIONS_BLUETOOTH = 99;
    private final String apiCode;

    private SensorTracker st;
    private AudioListener al;
    private BluetoothListener bl;

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

        setupAudioListener();

        // Set up and start sensor tracker with given sensors

        setupSensorTracker();

        // Set up switches

        setupSwitches();

        // Set up Bluetooth listener

        setupBluetoothListener();

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupAudioListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}
                    , Permissions.PERMISSION_RECORD_AUDIO);
            return;
        }
        setupAudio();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupAudio() {
        al = new AudioListener(this);

        al.startAudioRec();
    }


    private void setupSensorTracker() {
        st = new SensorTracker(this, al, 20,
                SensorManager.SENSOR_DELAY_FASTEST,   // Delay for all sensors
                Sensor.TYPE_ACCELEROMETER,            // Sensor 0
                Sensor.TYPE_GYROSCOPE,                // Sensor 1
                Sensor.TYPE_MAGNETIC_FIELD);          // Sensor 2

        st.start();
    }

    private void setupSwitches() {
        SwitchCompat rec_0 = findViewById(R.id.rec_0);
        SwitchCompat rec_1 = findViewById(R.id.rec_1);
        SwitchCompat rec_2 = findViewById(R.id.rec_2);
        SwitchCompat rec_3 = findViewById(R.id.rec_3);
        SwitchCompat rec_4 = findViewById(R.id.rec_4);
        SwitchCompat rec_5 = findViewById(R.id.rec_5);
        SwitchCompat[] switches = new SwitchCompat[]{rec_0, rec_1, rec_2, rec_3, rec_4, rec_5};
        SensorSwitchHandler ssh = new SensorSwitchHandler(this, switches);

        for (int i = 0; i < switches.length; i++) {
            String text = "Record " + Activities.values()[i];
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
    private void setupBluetoothListener() {

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
//                    , Permissions.PERMISSION_ACCESS_FINE_LOCATION);
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            this.requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
//                    , Permissions.PERMISSION_ACCESS_BACKGROUND_LOCATION);
//        }

    bl = new BluetoothListener(this);

        bl.startDiscovery();
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Permissions.PERMISSION_RECORD_AUDIO:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}
                            , Permissions.PERMISSION_RECORD_AUDIO);
                } else {
                    setupAudio();
                }
                break;
//            case Permissions.PERMISSION_ACCESS_FINE_LOCATION:
//                if (grantResults.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
//                            , Permissions.PERMISSION_ACCESS_FINE_LOCATION);
//                }
//                break;
//            case Permissions.PERMISSION_ACCESS_BACKGROUND_LOCATION:
//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
//                            , Permissions.PERMISSION_ACCESS_BACKGROUND_LOCATION);
//                }
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bl.destroy();
    }


}