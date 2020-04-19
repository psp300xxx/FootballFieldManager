package com.example.footballfieldmanager.model;

import android.os.Parcelable;

import java.io.Serializable;

public class SportCenter implements Serializable {

    private long id;

    private String location, name;
    private double latitude, longitude;

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        Builder(){

        }

        private long id;

        private String location, name;
        private double latitude, longitude;

        public long getId() {
            return id;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public String getName(){
            return name;
        }

        public String getLocation() {
            return location;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public double getLatitude() {
            return latitude;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public double getLongitude() {
            return longitude;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public SportCenter build(){
            SportCenter center = new SportCenter();
            center.setId(id);
            center.setLocation(location);
            center.setLatitude(latitude);
            center.setLongitude(longitude);
            center.setName(name);
            return center;
        }


    }

    private SportCenter(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name){this.name = name;}

    public String getName(){
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
