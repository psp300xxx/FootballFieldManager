package com.example.footballfieldmanager.controller.sport_center;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.footballfieldmanager.config.Configuration;
import com.example.footballfieldmanager.config.Protocol;
import com.example.footballfieldmanager.config.Service;
import com.example.footballfieldmanager.controller.login.LDMLogin;
import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.SportCenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LDMSportCenter implements SportCenterIF {

    private SportCenterDelegate delegate;

    private Response.Listener<JSONObject> fieldListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(LDMSportCenter.this.delegate==null){
                return;
            }
            try {
                JSONArray array = response.getJSONArray("root");
                List<FootballField> list = new ArrayList<>();
                for( int i=0 ; i<array.length(); i++ ){
                    try {
                        list.add(  Utility.jsonToField(array.getJSONObject(i) ));
                    }
                    catch(JSONException exc){
                        exc.printStackTrace();
                        continue;
                    }
                }
                LDMSportCenter.this.delegate.footballFieldDownloaded(list);
            }catch (JSONException exc){
                LDMSportCenter.this.delegate.sportCenterDownloadFailed(exc);
            }
        }
    };

    private Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(LDMSportCenter.this.delegate==null){
                return;
            }
            try {
                JSONArray centerArray = response.getJSONArray("root");
                List<SportCenter> centers = new ArrayList<>();
                for( int i =0 ; i<centerArray.length(); i++ ){
                    try{
                        JSONObject jsonCenter = centerArray.getJSONObject(i);
                        SportCenter center = Utility.jsonToCenter(jsonCenter);
                        centers.add(center);
                    }
                    catch (JSONException jsonException){
                        continue;
//                        I continue to fill up the array jumping this sport center
                    }
                }
                LDMSportCenter.this.delegate.sportCenterDownloaded(centers);
            } catch (JSONException e) {
                e.printStackTrace();
                LDMSportCenter.this.delegate.sportCenterDownloadFailed(e);
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(LDMSportCenter.this.delegate == null){
                return;
            }
            LDMSportCenter.this.delegate.sportCenterDownloadFailed(error);
        }
    };

    @Override
    public void setDelegate(SportCenterDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public SportCenterDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void searchSportCenters(String city, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Protocol.HTTP, Service.SPORT_CENTERS) + "?city=" + city ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }


    @Override
    public void searchSportCenters(double latitude, double longitude, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Protocol.HTTP, Service.SPORT_CENTERS) + "?latitude=" + latitude + "&" + "longitude=" + longitude ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }

    @Override
    public void searchFieldsForCenter(int centerId, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Protocol.HTTP, Service.FIELD_FOR_CENTER);
        url += "/"+centerId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, fieldListener, errorListener);
        queue.add(request);
    }


}
