package com.example.footballfieldmanager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestQueueSingleton {
    private Lock lock = new ReentrantLock();
    private static RequestQueueSingleton instance;
    private RequestQueue queue;


    private RequestQueueSingleton(){

    }

    private RequestQueueSingleton(Context context)
    {
        lock.lock();
        queue = Volley.newRequestQueue(context);
        lock.unlock();
    }

    public static RequestQueueSingleton getInstance(Context context){
        if(RequestQueueSingleton.instance == null ){
            RequestQueueSingleton.instance = new RequestQueueSingleton(context);
        }

        return RequestQueueSingleton.instance;
    }

    public RequestQueue getQueue(){
        return queue;
    }
}
