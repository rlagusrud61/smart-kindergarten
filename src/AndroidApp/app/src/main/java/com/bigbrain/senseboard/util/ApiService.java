package com.bigbrain.senseboard.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.bigbrain.senseboard.McWrap;
import com.bigbrain.senseboard.NearbyDevice;
import com.bigbrain.senseboard.weka.Activities;
import com.bigbrain.senseboard.weka.VocalActivities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.nio.charset.StandardCharsets;
import java.util.Collection;


public class ApiService {

    private static final String URL_BASE = "https://ss.mineapple.net/api/";
    private static final String ACTIVITY_URL = URL_BASE + "students/activity/";
    private static final String VOCAL_ACTIVITY_URL = URL_BASE + "students/vocalactivity/";
    private static final String PROXIMITY_URL = URL_BASE + "bluetooth/";
    private final RequestQueue queue;
    private final McWrap mcWrap;

    public ApiService(RequestQueue queue, McWrap mcWrap) {
        this.queue = queue;
        this.mcWrap = mcWrap;
    }

    public void updateActivity(Activities activity) {
        if (mcWrap.hardwareAddress == null) return;
        StringRequest request = new StringRequest(Request.Method.PUT,
                String.format("%s?activity=%s&hardwareAddress=%s", ACTIVITY_URL, activity.name(), mcWrap.hardwareAddress),
                response -> {}, error -> Log.e("VOLLEY", error.toString())) {
        };
        queue.add(request);
    }

    public void updateVocalActivity(VocalActivities activity) {
        if (mcWrap.hardwareAddress == null) return;
        StringRequest request = new StringRequest(Request.Method.PUT,
                String.format("%s?activity=%s&hardwareAddress=%s", VOCAL_ACTIVITY_URL, activity.name(), mcWrap.hardwareAddress),
                response -> {}, error -> Log.e("VOLLEY", error.toString())) {
        };
        queue.add(request);
    }

    public static final TypeReference<Collection<NearbyDevice>> REF_OBJECT = new TypeReference<Collection<NearbyDevice>>() {};


    public void updateNearbyDevices(Collection<NearbyDevice> devices) {
        if (mcWrap.hardwareAddress == null) return;

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

        try {
            String json = mapper.writerFor(REF_OBJECT).writeValueAsString(devices);
            StringRequest request = new StringRequest(Request.Method.PUT,
                    PROXIMITY_URL + mcWrap.hardwareAddress,
                    response -> Log.i("VOLLEY", response), error -> Log.e("VOLLEY", error.toString())) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    return json.getBytes(StandardCharsets.UTF_8);
                }
            };
            queue.add(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
