package com.bigbrain.senseboard.sensor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bigbrain.senseboard.weka.VocalActivities;

import java.util.Arrays;
import java.util.stream.IntStream;

public class AudioTester extends Thread {
    private final AudioListener audioListener;
    private final AudioHandler audioHandler;
    private final int pollingDelay;
    private String[] sounds = {"loud sound", "talking", "silent"};
    private int[] resultCount = {0,0,0,0};
    private double[] weights = {10, 15, 1, 0.5};
    private long timeTaken = 0;
    private int lastResult = -1;




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
            //System.out.println("data size: " + data.length + ", last entry: " + data[15999]);

            double result = audioHandler.classifyExperimental(data);

            resultCount[(int) result] += 1;

            if(resultSum() >=50){
                System.out.println("time taken: " + (System.currentTimeMillis()-timeTaken)+ "\n");
                determineAverageSound();
                resultCount = new int[]{0, 0, 0, 0};
                timeTaken = System.currentTimeMillis();
            }


            //System.out.println("audio classification: " + sounds[(int) result]);
            //System.out.println("time taken: " + (System.currentTimeMillis()-time)+ "\n");

            try {
                sleep(this.pollingDelay - Math.min((System.currentTimeMillis() - time), this.pollingDelay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    int resultSum(){
        int output = 0;
        for (int i=0; i<resultCount.length; i++) output +=resultCount[i];
        return output;
    }

    void determineAverageSound(){
        double max = 0;
        int bestSound = -1;
        for(int i=0; i<resultCount.length; i++){
            if((double)resultCount[i]*weights[i]>max){
                max = resultCount[i]*weights[i];
                bestSound = i;
            }

        }
        lastResult = bestSound;
        System.out.println("average sound: " + sounds[bestSound]);
        System.out.println("resultCount" + Arrays.toString(resultCount));
    }

    int getLastResult(){
        return lastResult;
    }
}
