package com.bigbrain.senseboard.weka;

public enum VocalActivities {
    CRYING("Crying"),
    LAUGHING("Laughing"),
    SHOUTING("Shouting"),
    TALKING("Talking"),
    SILENT("Silent");

    public final String label;
    VocalActivities(String label) {
        this.label = label;
    }
}
