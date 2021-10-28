package com.bigbrain.senseboard.util;

import android.content.Context;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;

import java.util.Arrays;


public class SensorSwitchHandler {
    Context context;

    SwitchCompat[] switches;

    SwitchCompat active;


    public SensorSwitchHandler(Context context, SwitchCompat[] switches) {
        this.context = context;
        this.switches = switches;
    }


    /**
     * Deactivate all switches except for one
     * @param s SwitchCompat that is to be the only active one
     */
    public void deactivateOthers(SwitchCompat s) {
        this.active = s;
        for (SwitchCompat s_o : switches) {
            if(!s_o.equals(s)) {
                s_o.setChecked(false);
            }
        }
    }

}
