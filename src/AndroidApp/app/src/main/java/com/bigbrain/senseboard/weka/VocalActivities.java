package com.bigbrain.senseboard.weka;

public enum VocalActivities {
    TALKING("Talking"),
    SHOUTING("Shouting"),
    CRYING("Crying"),
    SILENT("Silent"),
    LAUGHING("Laughing");

    public final String label;

    VocalActivities(String label) {
        this.label = label;
    }
}
