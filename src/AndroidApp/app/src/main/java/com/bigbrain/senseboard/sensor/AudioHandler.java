package com.bigbrain.senseboard.sensor;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class AudioHandler {
    private Instances instances;
    private Instance instance;
    private Classifier classifier;

    private final String[] sounds = {"TALKING", "SHOUTING", "CRYING", "SILENT", "LAUGHING"};
    public final int TALKING = 0;
    public final int SHOUTING = 0;
    public final int CRYING = 0;
    public final int SILENT = 0;
    public final int LAUGHING = 0;

    private int bufferSize;
    private String classifierPath = "Bruh.model";

    public AudioHandler(int buffer){
        bufferSize = buffer;
        ArrayList<Attribute> attributes = new ArrayList<>();

        for(int i=0; i<bufferSize; i++){
            attributes.add(new Attribute(Integer.toString(i)));
        }

        ArrayList<String> soundList = new ArrayList<>();
        for(int i=0; i<sounds.length; i++){
            soundList.add(sounds[i]);
        }
        attributes.add(new Attribute("Sounds", soundList));

        instances = new Instances("Bruh", attributes, 1);

        try {
            classifier = (Classifier) weka.core.SerializationHelper.read(classifierPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int classifyAudio(double[] audioBuffer){
        int result = -1;
        if(audioBuffer.length != bufferSize) return -1;

        instance = new DenseInstance(1, audioBuffer);
        instance.setDataset(instances);

        try {
            result = (int)classifier.classifyInstance(instance);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int classifyAudio(short[] audioBuffer) {
        int result = -1;
        if(audioBuffer.length != bufferSize) return -1;

        double[] fixedBuffer = new double[audioBuffer.length];
        for(int i=0; i<fixedBuffer.length; i++) {
            fixedBuffer[i] = (double) audioBuffer[i];
        }

        instance = new DenseInstance(1, fixedBuffer);
        instance.setDataset(instances);

        try {
            result = (int)classifier.classifyInstance(instance);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
