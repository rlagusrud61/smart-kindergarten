package com.bigbrain.senseboard.sensor;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class AudioTester extends Thread {
    private final AudioListener audioListener;
    private final AudioHandler audioHandler;
    private final int pollingDelay;



    public AudioTester(AudioHandler audioHandler, AudioListener audioListener, int pollingDelay) {
        this.audioHandler = audioHandler;
        this.audioListener = audioListener;
        this.pollingDelay = pollingDelay;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void run() {

        while(true) {
            long time = System.currentTimeMillis();

            short[] data = audioListener.readBuffer(audioHandler.bufferSize);
            double result = audioHandler.classifyAudio(data);

            System.out.println("audio classification: " + result);


            try {
                sleep(this.pollingDelay - Math.min((System.currentTimeMillis() - time), this.pollingDelay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
