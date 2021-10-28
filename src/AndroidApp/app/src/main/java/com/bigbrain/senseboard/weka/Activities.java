package com.bigbrain.senseboard.weka;

public enum Activities {
    SITTING("Sitting"),
    WALKING("Walking"),
    RUNNING("Running"),
    PLAYING("Playing"),
    FIGHTING("Fighting"),
    FALLING("Falling"),

    TALKING("Talking"),
    SHOUTING("Shouting"),
    CRYING("Crying"),
    SILENT("Silent"),
    LAUGHING("Laughing");

    public final String label;

    Activities(String label) {
        this.label = label;
    }
}
