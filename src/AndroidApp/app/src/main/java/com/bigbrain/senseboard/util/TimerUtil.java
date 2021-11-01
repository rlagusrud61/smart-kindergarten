package com.bigbrain.senseboard.util;

import android.content.Context;
import android.os.CountDownTimer;

import com.bigbrain.senseboard.MainActivity;
import com.bigbrain.senseboard.weka.Activities;

public class TimerUtil {

    private static CountDownTimer cdt;

    public static boolean cancelTimer() {
        if (cdt != null) {
            cdt.cancel();
            return true;
        }
        return false;
    }


    public static void startCountDown(MainActivity context, Activities activity, long millis, long interval) {
        cdt = new CountDownTimer(millis, interval) {
            @Override
            public void onTick(long l) {
                context.setTime(l);
            }

            @Override
            public void onFinish() {
                context.setTimeDone();
                context.doRecord(activity);
//                context.startRecordCycle(activity);
                cancelTimer();
            }
        }.start();
    }

}
