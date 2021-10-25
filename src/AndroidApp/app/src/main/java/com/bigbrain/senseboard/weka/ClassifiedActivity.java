package com.bigbrain.senseboard.weka;

public class ClassifiedActivity {
    public final static int SITTING = 0;
    public final static int WALKING = 1;
    public final static int RUNNING = 2;
    public final static int PLAYING = 3;
    public final static int FIGHTING = 4;
    public final static int FALLING = 5;


    public static String getActivityName(int activityID) {
        switch(activityID){
            case SITTING:
                return "sitting";
            case WALKING:
                return "walking";
            case RUNNING:
                return "running";
            case PLAYING:
                return "playing";
            case FIGHTING:
                return "fighting";
            case FALLING:
                return "falling";
            default:
                return "SENSOR_NOT_FOUND";
        }
    }
}
