package com.example.footballfieldmanager.controller.sport_center;

import com.android.volley.RequestQueue;

public interface SportCenterIF {


    void setDelegate(SportCenterDelegate delegate);

    SportCenterDelegate getDelegate();

    void searchSportCenters(String city, RequestQueue queue);

    void searchSportCenters(double latitude, double longitude, RequestQueue queue);

    void searchFieldsForCenter(int centerId, RequestQueue queue);

}
