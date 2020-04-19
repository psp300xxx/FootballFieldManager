package com.example.footballfieldmanager.controller.login;

import com.android.volley.RequestQueue;

public interface LoginIF {

    void setDelegate(LoginDelegate delegate);

    LoginDelegate getDelegate();

    void login(String id, String password, RequestQueue queue);

}
