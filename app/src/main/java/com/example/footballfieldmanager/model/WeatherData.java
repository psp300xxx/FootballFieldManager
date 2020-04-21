package com.example.footballfieldmanager.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class WeatherData implements Serializable {
    private Date date;
    private double celsiusTemperature;
    private String weather;

    private WeatherData(){

    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Date date;
        private double celsiusTemperature;
        private String weather;

        public Date getDate() {
            return date;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public double getCelsiusTemperature() {
            return celsiusTemperature;
        }

        public Builder setCelsiusTemperature(double celsiusTemperature) {
            this.celsiusTemperature = celsiusTemperature;
            return this;
        }

        public String getWeather() {
            return weather;
        }

        public Builder setWeather(String weather) {
            this.weather = weather;
            return this;
        }

        public WeatherData build(){
            WeatherData weatherData = new WeatherData();
            weatherData.setCelsiusTemperature(celsiusTemperature);
            weatherData.setDate(date);
            weatherData.setWeather(weather);
            return weatherData;
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCelsiusTemperature() {
        return celsiusTemperature;
    }

    public void setCelsiusTemperature(double celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @NonNull
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        String date = dateFormat.format(this.date);
        String tempFormat = String.format("%.2f", celsiusTemperature);
        String completeString = date + " - " + tempFormat + " Celsius - " + weather;
        return completeString;
    }
}
