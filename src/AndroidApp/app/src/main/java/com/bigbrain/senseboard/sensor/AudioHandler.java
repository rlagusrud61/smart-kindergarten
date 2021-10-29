package com.bigbrain.senseboard.sensor;

import com.bigbrain.senseboard.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class AudioHandler {
    private Instances instances;
    private Instance instance;
    private Classifier classifier;

    private final MainActivity mainActivity;




    private final String[] sounds = {"crying", "laughing", "shouting", "talking", "silent"};

    public final int bufferSize;
    private String classifierPath = "bruh.model";

    public AudioHandler(int buffer, MainActivity mainActivity){
        this.mainActivity = mainActivity;
        bufferSize = buffer;
        ArrayList<Attribute> attributes = new ArrayList<>();

        for(int i=1; i<=bufferSize; i++){
            attributes.add(new Attribute(Integer.toString(i)));
        }

        ArrayList<String> soundList = new ArrayList<>();
        for(int i=0; i<sounds.length; i++){
            soundList.add(sounds[i]);
        }
        attributes.add(new Attribute(Integer.toString(bufferSize+1), soundList));

        instances = new Instances("Bruh", attributes, 5);
        instances.setClassIndex(bufferSize);

        try {
            classifier = (Classifier) weka.core.SerializationHelper.read(mainActivity.getAssets().open("bruh.model"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public double classifyAudio(short[] audioBuffer) {
        double result = -1;
        if(audioBuffer.length != bufferSize) return -1;

        double[] fixedBuffer = new double[bufferSize+1];
        for(int i=0; i<bufferSize; i++) {
            fixedBuffer[i] = (double) audioBuffer[i]/65536;
        }

        instance = new DenseInstance(1, fixedBuffer);
        instance.setDataset(instances);

        long time = System.currentTimeMillis();

        try {
            result = classifier.classifyInstance(instance);
            System.out.println(Arrays.toString(fixedBuffer));
            System.out.println("time elapsed: " + (System.currentTimeMillis()-time));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
