package com.example.footballfieldmanager.config;

import androidx.annotation.NonNull;

public enum Service {
    LOGIN, FACEBOOK_LOGIN, SPORT_CENTERS, FIELD_FOR_CENTER, RENT_FOR_FIELD, BOOK_FIELD, SEARCH_USERS, SEARCH_RENTS_FOR_USER;

    @NonNull
    @Override
    public String toString() {
        if(this == LOGIN){
            return "/login";
        }
        else if(this == FACEBOOK_LOGIN){
            return "/auth/facebook";
        }
        else if(this == SPORT_CENTERS){
            return "/centers";
        }
        else if(this == FIELD_FOR_CENTER){
            return "/fieldsFor";
        }
        else if(this == RENT_FOR_FIELD){
            return "/fieldBookedSpaces";
        }
        else if(this == BOOK_FIELD){
            return "/bookField";
        }
        else if(this == SEARCH_USERS){
            return "/searchFriends";
        }
        else if(this == SEARCH_RENTS_FOR_USER){
            return "/rentForUser";
        }
        return super.toString();
    }
}
