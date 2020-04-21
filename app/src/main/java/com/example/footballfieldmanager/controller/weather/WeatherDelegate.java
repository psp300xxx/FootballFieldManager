package com.example.footballfieldmanager.controller.weather;

import com.example.footballfieldmanager.model.WeatherData;

import java.util.List;

public interface WeatherDelegate {

    void weatherDataDownloaded(List<WeatherData> weatherDataList);

    void operationFailed(Exception exc);

}
