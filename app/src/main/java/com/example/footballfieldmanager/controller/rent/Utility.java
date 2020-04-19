package com.example.footballfieldmanager.controller.rent;

import com.example.footballfieldmanager.model.FootballFieldRent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Utility {



    private Utility(){

    }

    public static List<FootballFieldRent> jsonToRents(JSONArray array) throws JSONException, ParseException {
        Map<Integer, FootballFieldRent> map = new HashMap<>();
        for( int i = 0; i<array.length(); i++ ){
            JSONObject object = array.getJSONObject(i);
            int rentId = object.getInt("football_field_rent_id");
            if(map.containsKey(rentId)){
                FootballFieldRent rent = map.get(rentId);
                rent.addUserId( object.getString("user") );
            }
            else {
                FootballFieldRent.Builder builder = FootballFieldRent.newBuilder();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                java.util.Date javaDate = format.parse( object.getString("datetime") );
                Date date = new Date(javaDate.getTime());
                builder.setDate(date).setId(rentId).addUserId(object.getString("user"));
                builder.setFieldId( object.getInt("field_id") );
                map.put(rentId, builder.build());
            }
        }
        return new ArrayList<>(map.values());
    }



}
