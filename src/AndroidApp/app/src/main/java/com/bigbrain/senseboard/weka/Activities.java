package com.bigbrain.senseboard.weka;

// String[] order "Falling", "Running","Sitting", "Playing", "Fighting", "Walking"
public enum Activities {
    FALLING("Falling"),
    RUNNING("Running"),
    SITTING("Sitting"),
    PLAYING("Playing"),
    FIGHTING("Fighting"),
    WALKING("Walking");

    public final String label;

    Activities(String label) {
        this.label = label;
    }
}
//    Screenshot order
//    SITTING("Sitting"),
//    PLAYING("Playing"),
//    WALKING("Walking"),
//    FALLING("Falling"),
//    FIGHTING("Fighting"),
//    RUNNING("Running");