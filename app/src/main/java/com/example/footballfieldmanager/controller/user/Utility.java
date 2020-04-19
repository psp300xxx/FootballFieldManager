package com.example.footballfieldmanager.controller.user;

import com.example.footballfieldmanager.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    private Utility(){

    }

    public static User jsonToUser(JSONObject object) throws JSONException {
        User.Builder builder = User.newBuilder();
        builder.setUsername(object.getString("username")).setId(object.getString("id"));
        return builder.build();
    }
}
