package com.bigbrain.senseboard.weka;
import android.content.res.AssetManager;
import android.util.Log;

import com.bigbrain.senseboard.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class ClassifyActivity {

    private static final String TAG = "Classify Activity";
    Classifier cls;
    float acc1, acc2, acc3, gyr1, gyr2, gyr3, Gyr3, mgf1, mgf2, mgf3;
    boolean running = true;
    ArrayList<Attribute> fvWekaAttributes;
    private final String[] activity = {"Falling", "Running","Sitting", "Playing", "Fighting", "Walking"};
    Instances instances;
    Instance instance;
    HashMap<Integer,Integer> readings;
    private final double[] activityWeights = {1, 1, 1, 1, 1, 1};
    String current_state;


    public void loadClassifier() {
        createTrainingSet();
        try {
            cls = (Classifier) weka.core.SerializationHelper.read(MainActivity.getStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initiateReadings();
    }

    public void activityPredict() {
        if (cls == null) {
            Log.d(TAG, "classifier is null");
            return;
        }

        int prediction = 0;
        try {

            double[] attrValues = new double[9];
            attrValues[0] = acc1;
            attrValues[1] = acc2;
            attrValues[2] = acc3;
            attrValues[3] = gyr1;
            attrValues[4] = gyr2;
            attrValues[5] = gyr3;
            attrValues[6] = mgf1;
            attrValues[7] = mgf2;
            attrValues[8] = mgf3;

            //Fill in the training set with one instance
            instance = new DenseInstance(1, attrValues);
            instance.setDataset(instances);


            // Get the prediction in int
            prediction = (int)cls.classifyInstance(instance);

            int count = readings.containsKey(prediction) ? readings.get(prediction) : 0;
            readings.put(prediction, count + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void createTrainingSet(){

        if (running) {

            Log.d(TAG, "Created training set...");
            // Create the attributes
            Attribute Wrist_Ax = new Attribute("Wrist_Ax");
            Attribute Wrist_Ay = new Attribute("Wrist_Ay");
            Attribute Wrist_Az = new Attribute("Wrist_Az");
            Attribute Wrist_Gx = new Attribute("Wrist_Gx");
            Attribute Wrist_Gy = new Attribute("Wrist_Gy");
            Attribute Wrist_Gz = new Attribute("Wrist_Gz");
            Attribute Wrist_Mx = new Attribute("Wrist_Mx");
            Attribute Wrist_My = new Attribute("Wrist_My");
            Attribute Wrist_Mz = new Attribute("Wrist_Mz");

            ArrayList<String> acList = new ArrayList<>();
            acList.addAll(Arrays.asList(activity));
            Attribute Activity = new Attribute("Activity", acList);

            fvWekaAttributes = new ArrayList<>(10);

            // For each position, we add an attribute
            fvWekaAttributes.add(Wrist_Ax);
            fvWekaAttributes.add(Wrist_Ay);
            fvWekaAttributes.add(Wrist_Az);
            fvWekaAttributes.add(Wrist_Gx);
            fvWekaAttributes.add(Wrist_Gy);
            fvWekaAttributes.add(Wrist_Gz);
            fvWekaAttributes.add(Wrist_Mx);
            fvWekaAttributes.add(Wrist_My);
            fvWekaAttributes.add(Wrist_Mz);
            fvWekaAttributes.add(Activity);
        }
    }

    public void putValues() {
        //ADD VALUES HERE FROM SENSORS
//        acc1 =;
//        acc2 =;
//        acc3 =;
//        gyr1 =;
//        gyr2 =;
//        gyr3 =;
//        mgf1 =;
//        mgf2 =;
//        mgf3 =;
        activityPredict();
        getPredictedActivity();
    }

    public void initiateReadings(){
        if (running) {
            readings = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                readings.put(i, 0);
            }
        }
    }

    public int getActivityWithMostOccurrence(){
        // Get the activity with the most occurrence
        double maxPredictionValue = 0;
        double weightedValue;
        int maxPredictionKey = 0;
        for (Map.Entry<Integer, Integer> entry : readings.entrySet()){
            weightedValue = (double) entry.getValue() * activityWeights[entry.getKey()];
            Log.d(TAG, "entry: " + entry.getKey() + " weighted value: " + weightedValue);
            if (maxPredictionValue < entry.getValue())
            {
                maxPredictionValue = weightedValue;
                maxPredictionKey = entry.getKey();
            }
        }return maxPredictionKey;
    }

    public void getPredictedActivity(){
        int sum = 0;
        for (int v: readings.values()){
            sum += v;
        }

        //Log.d(TAG, "Old activity: " + oldPredictionActivity);
        //if it reaches 150 readings
        if (sum == 150){
            int prediction = getActivityWithMostOccurrence();
            current_state = activity[prediction];
            Log.d(TAG, current_state);
            }

            readings.clear();

        }
}




