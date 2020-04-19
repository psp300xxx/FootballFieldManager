package com.example.footballfieldmanager.controller.sport_center;

import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.SportCenter;

import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    private Utility(){

    }

    public static SportCenter jsonToCenter(JSONObject jsonObject) throws JSONException {
        SportCenter.Builder builder = SportCenter.newBuilder();
        builder.setId(jsonObject.getLong("id")).setLatitude(jsonObject.getDouble("latitude"));
        builder.setLongitude(jsonObject.getDouble("longitude")).setLocation(jsonObject.getString("location"));
        builder.setName(jsonObject.getString("name"));
        return builder.build();
    }

    public static FootballField jsonToField(JSONObject jsonObject) throws JSONException{
        FootballField.Builder builder = FootballField.newBuilder();
        builder.setId( jsonObject.getInt("id") ).setNumberPlayers( jsonObject.getInt("nr_players") );
        boolean isIndoor = jsonObject.getInt("is_indoor") != 0;
        builder.setIndoor(isIndoor).setPitchType(jsonObject.getString("pitch_type"));
        return builder.build();
    }

}
