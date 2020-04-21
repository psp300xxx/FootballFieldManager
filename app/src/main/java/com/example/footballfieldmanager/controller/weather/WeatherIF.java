package com.example.footballfieldmanager.controller.weather;

import com.android.volley.RequestQueue;

public interface WeatherIF {

    void setDelegate(WeatherDelegate delegate);

    WeatherDelegate getDelegate();

    void searchWeatherData(double latitude, double longitude, RequestQueue queue);


}
