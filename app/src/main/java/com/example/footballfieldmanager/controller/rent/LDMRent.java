package com.example.footballfieldmanager.controller.rent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.footballfieldmanager.config.Configuration;
import com.example.footballfieldmanager.config.Protocol;
import com.example.footballfieldmanager.config.Service;
import com.example.footballfieldmanager.controller.sport_center.LDMSportCenter;
import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.FootballFieldRent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LDMRent implements RentIF{

    private RentDelegate delegate;
    private final String NO_ERROR_STRING = "NOE";

    private Response.Listener<JSONObject> fieldListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(LDMRent.this.delegate==null){
                return;
            }
            try {
                JSONArray array = response.getJSONArray("root");
                List<FootballFieldRent> list = Utility.jsonToRents(array);
                LDMRent.this.delegate.futureRentDownloaded(list);
            }catch (JSONException| ParseException exc){
                exc.printStackTrace();
                LDMRent.this.delegate.downloadFailed(exc);
            }
        }
    };

    private Response.Listener<JSONObject> bookingListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(LDMRent.this.delegate==null){
                return;
            }
            try {
                String result = response.getString("error");
                if(result.equals(NO_ERROR_STRING)){
                    LDMRent.this.delegate.fieldbookedCorrectly();
                    return;
                }
                Exception exception = new Exception(result);
                LDMRent.this.delegate.downloadFailed(exception);
            }catch (JSONException exc){
                exc.printStackTrace();
                LDMRent.this.delegate.downloadFailed(exc);
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(LDMRent.this.delegate == null){
                return;
            }
            LDMRent.this.delegate.downloadFailed(error);
        }
    };

    @Override
    public void setDelegate(RentDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public RentDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void searchFutureRents(int fieldId, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Service.RENT_FOR_FIELD);
        url += "?id=" + fieldId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, fieldListener, errorListener);
        queue.add(request);
    }

    @Override
    public void bookField(FootballFieldRent fieldRent, int centerId, String token, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Service.BOOK_FIELD);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("center_id", ""+centerId);
        bodyMap.put("field_id",""+ fieldRent.getFieldId());
        String formatDate = "YYYY-MM-dd HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        String dateString = dateFormat.format(fieldRent.getDate());
        bodyMap.put("datetime", dateString );
        bodyMap.put("token", token);
        JSONObject bodyJson = new JSONObject(bodyMap);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, bodyJson, bookingListener, errorListener);
        queue.add(request);
    }
}
