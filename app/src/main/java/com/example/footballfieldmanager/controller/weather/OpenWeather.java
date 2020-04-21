package com.example.footballfieldmanager.controller.weather;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.footballfieldmanager.controller.user.LDMUserManager;
import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OpenWeather implements WeatherIF {

    private WeatherDelegate delegate;

    private final String SERVICE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private final String APP_ID = "cfbc1077df252448977c8c91c43a4628";
    private final int CORRECT_CODE = 200;

    private Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(OpenWeather.this.delegate==null){
                return;
            }
            try {
                int code = Integer.parseInt(response.getString("cod"));
                if(code != CORRECT_CODE){
                    Exception exception = new Exception("incorrect code, code="+code);
                    OpenWeather.this.delegate.operationFailed(exception);
                    return;
                }
                JSONArray array = response.getJSONArray("list");
                List<WeatherData> weatherDataList = new ArrayList<>();
                for(int i =0 ; i<array.length();i++){
                    JSONObject currentObject = array.getJSONObject(i);
                    WeatherData newData = Utility.jsonToWeatherData(currentObject);
                    weatherDataList.add( newData );
                }
                OpenWeather.this.delegate.weatherDataDownloaded(weatherDataList);
            }catch (JSONException |ParseException exc){
                exc.printStackTrace();
                OpenWeather.this.delegate.operationFailed(exc);
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(OpenWeather.this.delegate == null){
                return;
            }
            OpenWeather.this.delegate.operationFailed(error);
        }
    };


    @Override
    public void setDelegate(WeatherDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public WeatherDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void searchWeatherData(double latitude, double longitude, RequestQueue queue) {
        String url = SERVICE_URL + ("?lat=" + latitude);
        url+="&lon="+longitude;
        url+="&appid="+APP_ID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }
}
