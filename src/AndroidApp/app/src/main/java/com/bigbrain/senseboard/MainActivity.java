package com.bigbrain.senseboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bigbrain.senseboard.sensor.AudioHandler;
import com.bigbrain.senseboard.sensor.AudioListener;
import com.bigbrain.senseboard.sensor.AudioTester;
import com.bigbrain.senseboard.sensor.BluetoothListener;
import com.bigbrain.senseboard.sensor.SensorTracker;
import com.bigbrain.senseboard.util.SensorSwitchHandler;
import com.bigbrain.senseboard.util.TimerUtil;
import com.bigbrain.senseboard.weka.Activities;

import org.apache.commons.lang3.RandomStringUtils;


public class MainActivity extends AppCompatActivity {

//    private static final int PERMISSIONS_RECORD_AUDIO = 98;
//    private static final int PERMISSIONS_BLUETOOTH = 99;
    private final String apiCode;

    private SensorTracker st;
    private AudioListener al;
    private BluetoothListener bl;
    private AudioHandler ah;
    private AudioTester at;

    private TextView time;

    private EditText enterMAC;
    private Button buttonMAC;
    private TextView currentActivity;
    private final long MEASUREMENT_DELAY = 3000;

    private RequestQueue volleyQueue;
    private final McWrap mcWrap = new McWrap();


    public MainActivity() {
        apiCode = RandomStringUtils.random(6, false, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start permission request chain (handled by onRequestPermissionsResult)
        this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}
                , Permissions.PERMISSION_RECORD_AUDIO);


        setupSensorTracker();
        setupSwitches();
        setupAudio();
        setupAudioHandler();
        setupMAC();
//        setupAudioTester();

        volleyQueue = Volley.newRequestQueue(this);

        this.time = findViewById(R.id.timerTextView);
        this.time.setText(formatTime(MEASUREMENT_DELAY));

        this.currentActivity = findViewById(R.id.currentActivity);
    }

    public void setCurrentActivity(Activities activity) {
        this.currentActivity.setText(activity != null ? activity.label : "");
    }

    private void setupMAC() {
        this.enterMAC = findViewById(R.id.editTextMAC);
        this.buttonMAC = findViewById(R.id.buttonMAC);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String value = sharedPref.getString(getString(R.string.addressMAC), null);

        SharedPreferences.Editor editor = sharedPref.edit();
        buttonMAC.setOnClickListener(view -> {
            String mac = String.valueOf(enterMAC.getText());
            System.out.println("bruh");
            editor.remove(getString(R.string.addressMAC));
            editor.putString(getString(R.string.addressMAC), mac);
            editor.apply();
            mcWrap.hardwareAddress = mac;
        });

        if (value != null) {
            enterMAC.setText(value);
        }

    }

    @SuppressLint("DefaultLocale")
    private String formatTime(long millis) {
        this.time.setTextColor(Color.BLACK);
        return String.format("%02d", (int) Math.floor((double) millis / 1000))
                + ":"
                + String.format("%02d", millis % 1000).substring(0, 2);
    }

    public void setTime(long millis) {
        this.time.setText(formatTime(millis));
    }

    public void setTimeDone() {
        this.time.setTextColor(Color.GREEN);
        this.time.setText(R.string.recordingString);
    }

    public void startRecordCycle(Activities activity) {
        TimerUtil.cancelTimer();
        TimerUtil.startCountDown(this, activity, MEASUREMENT_DELAY, 10);
    }

    public void doRecord(Activities activity) {
        this.st.startRecord(activity);
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
                    startRecordCycle(Activities.values()[finalI]); // Ensure that i is an activity in ClassifiedActivity
//                    st.startRecord(finalI);
                } else {
                    st.stopRecord();
                    this.time.setText(formatTime(MEASUREMENT_DELAY));
                    TimerUtil.cancelTimer();
                }
            });
        }
    }

    private void setupAudioHandler() {
        ah = new AudioHandler(250, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupBluetoothListener() {
        bl = new BluetoothListener(this, 12000); //60000

        bl.start();
    }

    private void setupAudioTester() {
        at = new AudioTester(ah, al, 1000);
        at.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Permissions.PERMISSION_RECORD_AUDIO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                            , Permissions.PERMISSION_ACCESS_FINE_LOCATION);
                }
                break;
            case Permissions.PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                            , Permissions.PERMISSION_ACCESS_BACKGROUND_LOCATION);
                }
                break;
            case Permissions.PERMISSION_ACCESS_BACKGROUND_LOCATION:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All permissions given! Run dependent methods...

                    setupAudio();

                    setupBluetoothListener();

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bl.unregister();
    }
}