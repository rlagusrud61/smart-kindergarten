package com.bigbrain.senseboard.sensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.bigbrain.senseboard.MainActivity;

import java.util.Arrays;

public class AudioListener {
    private static final int PERMISSIONS_RECORD_AUDIO = 98;
    private MainActivity context;

    private AudioRecord recorder;
    private int bufferSize;
    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT; //Maybe switch to AAC

    private static final int BUFFER_CONSTANT = 2048;



    public AudioListener(MainActivity context) {
        this.context = context;
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, ENCODING);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void start() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            context.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
            , PERMISSIONS_RECORD_AUDIO);
            return;
        }


        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE, CHANNELS, ENCODING, BUFFER_CONSTANT);
        recorder.startRecording();
        short[] buffer = new short[bufferSize];
        while (true) {
            recorder.read(buffer, 0, bufferSize, AudioRecord.READ_BLOCKING);
            System.out.println(Arrays.toString(buffer));
        }
    }

    public void stop() {
        recorder.stop();
    }



}
