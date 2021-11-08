package com.bigbrain.senseboard.sensor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bigbrain.senseboard.MainActivity;

import java.util.Arrays;

public class AudioListener {
    private MainActivity context;
    private AudioRecord recorder;
    private int bufferSize;
    public static final int SAMPLE_RATE = 16000;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT; //Maybe switch to AAC

    private static final int BUFFER_CONSTANT = SAMPLE_RATE*2;



    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public AudioListener(MainActivity context) {
        this.context = context;
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, ENCODING);
        System.out.println("audioBuffer" + bufferSize);

        this.recorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION,
                SAMPLE_RATE, CHANNELS, ENCODING, BUFFER_CONSTANT);

    }

    public void startAudioRec() {
        recorder.startRecording();

//        if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
//        } else {
//            Log.d("Bruh", "Cannot initialize AudioRecord object in background");
//        }
    }

    public void stopAudioRec() {
        recorder.stop();
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public short[] readBuffer(int arraySize) {
        short[] data = new short[arraySize];
        recorder.read(data, 0, arraySize, AudioRecord.READ_NON_BLOCKING);
        return data;
    }




}
