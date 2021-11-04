package com.bigbrain.senseboard.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.bigbrain.senseboard.McWrap;
import com.bigbrain.senseboard.weka.Activities;
import com.bigbrain.senseboard.weka.VocalActivities;

public class ApiService {

    private static final String URL_BASE = "https://ss.mineapple.net/api/";
    private static final String ACTIVITY_URL = URL_BASE + "students/activity/";
    private static final String VOCAL_ACTIVITY_URL = URL_BASE + "students/activity/";
    private final RequestQueue queue;
    private final McWrap mcWrap;

    public ApiService(RequestQueue queue, McWrap mcWrap) {
        this.queue = queue;
        this.mcWrap = mcWrap;
    }

    public void updateActivity(Activities activity) {
        if(mcWrap.hardwareAddress == null) return;
        StringRequest request = new StringRequest(Request.Method.PUT,
                String.format("%s?activity=%s&hardwareAddress=%s", ACTIVITY_URL, activity.name(), mcWrap.hardwareAddress),
                response -> Log.i("VOLLEY", response), error -> Log.e("VOLLEY", error.toString())) {
        };
        queue.add(request);
    }

    public void updateVocalActivity(VocalActivities activity) {
        if(mcWrap.hardwareAddress == null) return;
        StringRequest request = new StringRequest(Request.Method.PUT,
                String.format("%s?activity=%s&hardwareAddress=%s", VOCAL_ACTIVITY_URL, activity.name(), mcWrap.hardwareAddress),
                response -> Log.i("VOLLEY", response), error -> Log.e("VOLLEY", error.toString())) {
        };
        queue.add(request);
    }
}
