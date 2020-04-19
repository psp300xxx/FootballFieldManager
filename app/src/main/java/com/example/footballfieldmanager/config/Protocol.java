package com.example.footballfieldmanager.config;

import androidx.annotation.NonNull;

public enum Protocol {
    HTTP, HTTPS;

    @NonNull
    @Override
    public String toString() {
        if(this==HTTP){
            return "http://";
        }
        else if(this == HTTPS){
            return "https://";
        }
        return super.toString();
    }
}
