package com.bigbrain.senseboard.sensor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bigbrain.senseboard.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import org.jtransforms.fft.DoubleFFT_1D;

public class AudioHandler extends Thread {
    //private Instances instances;
    //private Instance instance;
    //private Classifier classifier;

    private final AudioListener al;

    //private final MainActivity mainActivity;

    //private final int normalizeBound = 1000;

    //private final DoubleFFT_1D FFT;

    private final int pollingDelay;
    private String[] sounds = {"loud sound", "talking", "silent"};
    private int[] resultCount = new int[3];
    private double[] weights = {1, 1, 1};
    private long timeTaken = 0;
    private int lastResult = -1;
    private int resultAmount;

    public final int bufferSize;
    //private String classifierPath = "16000spectrumforestundeep.model";

    public AudioHandler(AudioListener al, MainActivity mainActivity, int buffer){
        //this.mainActivity = mainActivity;
        bufferSize = buffer;
        this.al = al;
        pollingDelay = 1000*buffer/this.al.SAMPLE_RATE;
        resultAmount = this.al.SAMPLE_RATE/buffer;

        ArrayList<Attribute> attributes = new ArrayList<>();

        for(int i=1299; i<=bufferSize; i++){
            attributes.add(new Attribute(Integer.toString(i)));
        }

        ArrayList<String> soundList = new ArrayList<>();
        for(int i=0; i<sounds.length; i++){
            soundList.add(sounds[i]);
        }
        attributes.add(new Attribute("99999", soundList));
/*
        instances = new Instances("Bruh", attributes, 5);
        instances.setClassIndex(instances.numAttributes()-1);

        try {
            classifier = (Classifier) weka.core.SerializationHelper.read(mainActivity.getAssets().open(classifierPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        FFT = new DoubleFFT_1D(bufferSize);
 */
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void run() {

        while(true) {
            long time = System.currentTimeMillis();

            short[] data = al.readBuffer(bufferSize);
            //System.out.println("data size: " + data.length + ", last entry: " + data[15999]);

            int result = classifyExperimental(data);

            resultCount[result] += 1;

            if(resultSum() >=resultAmount){
                //System.out.println("time taken: " + (System.currentTimeMillis()-timeTaken)+ "\n");
                determineAverageSound();
                resultCount = new int[3];
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

/*
    public double classifyTimeAudio(short[] audioBuffer) {
        double result = -1;
        if(audioBuffer.length != bufferSize) return -1;

        short max = maxValue(audioBuffer);
        if (max<1000) return 10;

        double[] fixedBuffer = new double[audioBuffer.length+1];
        for(int i=0; i<audioBuffer.length; i++) {
            fixedBuffer[i] = (double) audioBuffer[i]/max;
        }

        instance = new DenseInstance(1, fixedBuffer);
        instance.setDataset(instances);

        //long time = System.currentTimeMillis();

        try {
            result = classifier.classifyInstance(instance);
            //System.out.println(Arrays.toString(fixedBuffer));
            //System.out.println("time elapsed: " + (System.currentTimeMillis()-time));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

 */
/*
    double classifySpectrumAudio(short[] input){
        double result = -1;
        if(input.length != bufferSize) return -1;

        short max = maxValue(input);
        System.out.println("max: " + max);
        if (max<1000) return 10;

        double[] fixedBuffer = toDoubleArray(input, (double) max);


        fixedBuffer = performFFT(fixedBuffer);

        instance = new DenseInstance(1, fixedBuffer);
        instance.setDataset(instances);

        try {
            result = classifier.classifyInstance(instance);
            //System.out.println(Arrays.toString(fixedBuffer));
            //System.out.println("time elapsed: " + (System.currentTimeMillis()-time));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
 */

    int classifyExperimental(short[] input){
        short max = maxValue(input);
        //System.out.println("max: " + max);

        if(max<2000) return 2;
        else if(max<6000) return 1;
        return 0;
    }

    short maxValue(short[] input){
        short max = 1;
        for(short data : input) {
            short abs = (short) Math.abs(data);
            if(abs>max) max = abs;
        }
        return max;
    }
/*
    double maxValue(double[] input){
        double max = 1;
        for(double data : input) {
            double abs = Math.abs(data);
            if(abs>max) max = abs;
        }
        return max;
    }

 */
/* unused
    double[] normalize(double[] input){
        double max = 1;
        for(double data : input) {
            double abs = Math.abs(data);
            if(abs>max) max = abs;
        }
        for(double data : input) data = data/max;

        return input;
    }

 */
/*
    double[] toDoubleArray(short[] input, double max){
        double[] output = new double[input.length];
        for(int i=0; i<input.length; i++){
            output[i] = (double) input[i]/max;
        }
        return output;
    }

 */
/*
     double[] performFFT(double[] input){
        double[] complexBuffer = Arrays.copyOf(input, 2*bufferSize);
        double[] output = new double[bufferSize/2-1299];
        //System.out.println("check");

        FFT.realForwardFull(complexBuffer);

        for(int i=1299; i<bufferSize/2; i++) {
           output[i-1299] = (Math.sqrt(complexBuffer[2*i]*complexBuffer[2*i] + complexBuffer[2*i+1]*complexBuffer[2*i+1])/bufferSize);
        }
        //System.out.println("first: " + output[1] + ", last: " + output[bufferSize/2-1]);

        return output;
    }

 */
/*
    double[] removeNoise(double[] input){
        double max = maxValue(input)*0.1;
        double[] output = input;
        for(int i =0; i<output.length; i++){
            if(output[i]<max) output[i] = 0;
        }
        return output;


    }

 */
    int resultSum(){
        int output = 0;
        for (int i=0; i<resultCount.length; i++) output +=resultCount[i];
        return output;
    }
    void determineAverageSound(){
        double max = 0;
        int bestSound = -1;
        //System.out.println("weights length: " + weights.length + " , resultcount.length: " + resultCount.length);
        for(int i=0; i<resultCount.length; i++){
            if((double)resultCount[i]*weights[i]>max){
                max = resultCount[i]*weights[i];
                bestSound = i;
            }

        }
        lastResult = bestSound;
        System.out.println("average sound: " + sounds[bestSound]);
        //System.out.println("resultCount" + Arrays.toString(resultCount));
    }

    int getLastResult(){
        return lastResult;
    }



}
