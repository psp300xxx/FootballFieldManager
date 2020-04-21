package com.example.footballfieldmanager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestQueueSingleton {
    private static Lock lock = new ReentrantLock();
    private static RequestQueueSingleton instance;
    private RequestQueue queue;


    private RequestQueueSingleton(){

    }

    private RequestQueueSingleton(Context context)
    { ;
        queue = Volley.newRequestQueue(context);
    }

    public static RequestQueueSingleton getInstance(Context context){
        lock.lock();
        if(RequestQueueSingleton.instance == null ){
            RequestQueueSingleton.instance = new RequestQueueSingleton(context);
        }
        lock.unlock();
        return RequestQueueSingleton.instance;
    }

    public RequestQueue getQueue(){
        return queue;
    }
}
