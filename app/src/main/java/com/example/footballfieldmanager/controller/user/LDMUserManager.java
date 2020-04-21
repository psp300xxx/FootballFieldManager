package com.example.footballfieldmanager.controller.user;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.footballfieldmanager.config.Configuration;
import com.example.footballfieldmanager.config.Protocol;
import com.example.footballfieldmanager.config.Service;
import com.example.footballfieldmanager.controller.rent.LDMRent;
import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LDMUserManager implements UserIF {
    private UserDelegate delegate;

    private Response.Listener<JSONObject> userListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(LDMUserManager.this.delegate==null){
                return;
            }
            try {
                JSONArray array = response.getJSONArray("root");
                List<User> users = new ArrayList<>();
                for(int i =0 ; i<array.length(); i++){
                    JSONObject jsonUser = array.getJSONObject(i);
                    User newUser = Utility.jsonToUser(jsonUser);
                    users.add(newUser);
                }
                LDMUserManager.this.delegate.userCorrectlyDownloaded(users);
            }catch (JSONException exc){
                exc.printStackTrace();
                LDMUserManager.this.delegate.userOperationFailed(exc);
            }
        }
    };

    private Response.Listener<JSONObject> fieldListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if(LDMUserManager.this.delegate==null){
                return;
            }
            try {
                JSONArray array = response.getJSONArray("root");
                List<FootballFieldRent> rents = com.example.footballfieldmanager.controller.rent.Utility.jsonToRents(array);
                LDMUserManager.this.delegate.userFutureRentsDownloaded(rents);
            }catch (JSONException|ParseException exc){
                exc.printStackTrace();
                LDMUserManager.this.delegate.userOperationFailed(exc);
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(LDMUserManager.this.delegate == null){
                return;
            }
            LDMUserManager.this.delegate.userOperationFailed(error);
        }
    };

    @Override
    public void setDelegate(UserDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public UserDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void searchFriends(String name, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Service.SEARCH_USERS);
        url += "?name="+name;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, userListener, errorListener);
        queue.add(request);
    }

    @Override
    public void searchUserFutureRents(String userID, RequestQueue queue) {
        String url = Configuration.getInstance().getURL(Service.SEARCH_RENTS_FOR_USER);
        url += "?user_id="+userID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, fieldListener, errorListener);
        queue.add(request);
    }
}
