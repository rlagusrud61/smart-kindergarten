package com.bigbrain.senseboard.weka;

public enum Activities {
    PLAYING("Playing"),
    RUNNING("Running"),
    WALKING("Walking"),
    FIGHTING("Fighting"),
    SITTING("Sitting"),
    FALLING("Falling"),
    ;

    public final String label;

    Activities(String label) {
        this.label = label;
    }
}
