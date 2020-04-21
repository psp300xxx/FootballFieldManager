package com.example.footballfieldmanager.controller.weather;

import com.example.footballfieldmanager.model.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utility {

    private Utility(){

    }

    private static final double KELVIN_CELSIUS_CONSTANT = 273.15;

    public static WeatherData jsonToWeatherData(JSONObject jsonObject) throws JSONException, ParseException {
        String time = jsonObject.getString("dt_txt");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date javaDate = dateFormat.parse(time);
        Date date = new Date(javaDate.getTime());
        WeatherData.Builder builder = WeatherData.newBuilder();
        builder.setDate(date);
        JSONObject main = jsonObject.getJSONObject("main");
        double celsiusTemperature = main.getDouble("temp");
        celsiusTemperature-=KELVIN_CELSIUS_CONSTANT;
        builder.setCelsiusTemperature(celsiusTemperature);
        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        String weather = jsonArray.getJSONObject(0).getString("main");
        builder.setWeather(weather);
        return builder.build();
    }
}
