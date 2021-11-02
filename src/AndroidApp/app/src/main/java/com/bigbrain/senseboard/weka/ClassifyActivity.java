package com.bigbrain.senseboard.weka;
import android.content.res.AssetManager;
import android.service.autofill.Dataset;
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
    float acc1, acc2, acc3, gyr1, gyr2, gyr3, mgf1, mgf2, mgf3;
    boolean running = true;
    ArrayList<Attribute> fvWekaAttributes;
    private final String[] activity = {"Falling", "Running","Sitting", "Playing", "Fighting", "Walking"};
    Instances instances;
    Instance instance;
    HashMap<Integer,Integer> readings = new HashMap<>();
    private final double[] activityWeights = {1, 1, 1, 1, 1, 1};
    String current_state;


    public ClassifyActivity(MainActivity act) {
        createTrainingSet();
        try {
            cls = (Classifier) weka.core.SerializationHelper.read(act.getAssets().open("ModelJ48.model"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 6; i++) {
            this.readings.put(i, 0);
        }
        //System.out.println(readings);
    }

//    public void initiateReadings(){
//        System.out.println("initiate readings method called");
//        if (running) {
//            this.readings = new HashMap<>();
//            for (int i = 0; i < 6; i++) {
//                this.readings.put(i, 0);
//            }
//        }
//        System.out.println(readings); //readings is created
//    }

    public void activityPredict() {
        //System.out.println("activity predict called");
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
            instances = new Instances("test", fvWekaAttributes, 5);
            instances.setClassIndex(9);


            //Fill in the training set with one instance
            instance = new DenseInstance(1, attrValues);
            instances.add(instance);
            instance.setDataset(instances);


            // Get the prediction in int
            prediction = (int)cls.classifyInstance(instance);
            //System.out.println( "the prediction is: " + prediction);
            //System.out.println("the predicted activity is " + activity[prediction]);

            int count = 0;
            //System.out.println("does readings contain the key? " + this.readings.containsKey(prediction));
            if (this.readings.containsKey(prediction)) {
                count = this.readings.get(prediction);
                //System.out.println("the count is: " + count);
            }

            this.readings.put(prediction, count + 1);
            //System.out.println("readings" + this.readings);

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

    public void putValues(float[] row) {
        //ADD VALUES HERE FROM SENSORS
        acc1 = row[0];
        acc2 = row[1];
        acc3 =row[2];
        gyr1 =row[3];
        gyr2 =row[4];
        gyr3 =row[5];
        mgf1 =row[6];
        mgf2 =row[7];
        mgf3 =row[8];
        //System.out.println("in put values " + Arrays.toString(row));
        activityPredict();
        getPredictedActivity();
    }



    public int getActivityWithMostOccurrence(){
        //System.out.println("activity with most occurrence called");
        // Get the activity with the most occurrence
        double maxPredictionValue = 0;
        double weightedValue;
        int maxPredictionKey = 0;
        for (Map.Entry<Integer, Integer> entry : readings.entrySet()){
            weightedValue = (double) entry.getValue() * activityWeights[entry.getKey()];
//            Log.d(TAG, "entry: " + entry.getKey() + " weighted value: " + weightedValue);
            if (maxPredictionValue < entry.getValue())
            {
                maxPredictionValue = weightedValue;
                maxPredictionKey = entry.getKey();
            }
        }
//        System.out.println("Max predicted activity is: " + activity[maxPredictionKey]);
        //System.out.println("The max prediction key is " + maxPredictionKey);
        return maxPredictionKey;
    }

    public void getPredictedActivity(){
        //System.out.println("get predicted activity is called");
        int sum = 0;
        for (int v: readings.values()){
            sum += v;
            //System.out.println(sum);
        }

        //Log.d(TAG, "Old activity: " + oldPredictionActivity);
        //if it reaches 150 readings
        if (sum == 150){
            //System.out.println("reached sum == 150");
            int prediction = getActivityWithMostOccurrence();
            current_state = activity[prediction];
            for (int i = 0; i < 6; i++) {
                this.readings.put(i, 0);
            }
            //System.out.println("new readings =" + readings);
            //Log.d(TAG, current_state);
            }


        //System.out.println("readings cleared");

        }
}




