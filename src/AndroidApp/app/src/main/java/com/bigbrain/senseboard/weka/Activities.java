package com.bigbrain.senseboard.weka;

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