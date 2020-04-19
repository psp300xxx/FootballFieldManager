package com.example.footballfieldmanager.activities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefencesManager {

    private Context context;
    private final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    private static SharedPrefencesManager ourInstance = null;

    public static SharedPrefencesManager getInstance(Context context) {
        if( SharedPrefencesManager.ourInstance == null ){
            SharedPrefencesManager.ourInstance = new SharedPrefencesManager(context);
        }
        return ourInstance;
    }

    private SharedPrefencesManager(Context context) {
        this.context = context;
    }

    public void saveString(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value );
        editor.apply();
    }

    public String getString(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

        String value =sharedPreferences.getString(key, null);

        return value;
    }

}
