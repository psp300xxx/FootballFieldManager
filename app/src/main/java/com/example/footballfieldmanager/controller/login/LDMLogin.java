package com.example.footballfieldmanager.controller.login;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.footballfieldmanager.config.Configuration;
import com.example.footballfieldmanager.config.Protocol;
import com.example.footballfieldmanager.config.Service;

import org.json.JSONException;
import org.json.JSONObject;

public class LDMLogin implements LoginIF {

    private LoginDelegate delegate;

    @Override
    public void setDelegate(LoginDelegate delegate) {
        if( delegate != null ){
            this.delegate = delegate;
        }
    }

    @Override
    public LoginDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void login(String id, String password, RequestQueue queue) {
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(LDMLogin.this.delegate==null){
                    return;
                }
                try {
                    String token = response.getString("token");
                    String userId = response.getString("userId");
                    LDMLogin.this.delegate.isLoginSuccessfullyCompleted(token, userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LDMLogin.this.delegate.hasLoginFailed(e);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(LDMLogin.this.delegate == null){
                    return;
                }
                LDMLogin.this.delegate.hasLoginFailed(error);
            }
        };
        String url = Configuration.getInstance().getURL(Protocol.HTTP, Service.LOGIN) + "?id=" + id +"&password=" +password ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }
}
