package com.bigbrain.senseboard.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bigbrain.senseboard.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiUtil {
    private final static String BASE_URL = "https://ss.mineapple.net";

    public static void putArrayData(Context context, JSONArray content, String url_ext) {
        RequestQueue queue = Volley.newRequestQueue(context);  // this = context

        String url = BASE_URL + url_ext; //context.getString(R.string.putActivityURL)
        JsonArrayRequest putRequest = new JsonArrayRequest(Request.Method.PUT, url
                , content
                ,response -> {
                    // response
                    Log.d("Response", String.valueOf(response));
                },
                error -> {
                    // error
                    Log.d("Error.Response", String.valueOf(error));
                }
        );

        queue.add(putRequest);
    }

    public static void putData(Context context, JSONObject content, String url_ext) {
        RequestQueue queue = Volley.newRequestQueue(context);  // this = context

        String url = BASE_URL + url_ext; //context.getString(R.string.putActivityURL)
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url
                , content
                ,response -> {
            // response
            Log.d("Response", String.valueOf(response));
        },
                error -> {
                    // error
                    Log.d("Error.Response", String.valueOf(error));
                }
        );

        queue.add(putRequest);
    }
}
