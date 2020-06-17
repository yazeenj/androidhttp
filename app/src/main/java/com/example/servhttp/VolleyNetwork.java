package com.example.servhttp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyNetwork {

    private RequestQueue requestQueue;
    private Context ctx;
    private static VolleyNetwork instance;

    private VolleyNetwork(Context ctx){
        this.ctx = ctx;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized VolleyNetwork getInstance(Context ctx){
        if(instance == null){
            instance = new VolleyNetwork(ctx);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public void addToRequestQueue(Request req){
        getRequestQueue().add(req);
    }

}
