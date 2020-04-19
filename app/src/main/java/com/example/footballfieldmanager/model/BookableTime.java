package com.example.footballfieldmanager.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

public class BookableTime {

    private static final int [] AVAILABLE_TIMES = {9,10,11,12,13,14,15,16,17,18,19,20,21,22};

    public static BookableTime [] allTimes(){
        BookableTime [] times = new BookableTime[AVAILABLE_TIMES.length];
        for (int i = 0 ; i<AVAILABLE_TIMES.length ; i++){
            try {
                times[i] = new BookableTime(AVAILABLE_TIMES[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                //should never happen
            }
        }
        return times;
    }

    private int hour;

    private boolean arrayContains(int [] arr,int number){
        for (int i =0 ; i< arr.length ; i++){
            if( arr[i] == number ){
                return true;
            }
        }
        return false;
    }

    public BookableTime(int hour) throws IllegalAccessException {
        if( !arrayContains(AVAILABLE_TIMES, hour) ){
            throw new IllegalAccessException("HOUR NOT AVAILABLE");
        }
        this.hour = hour;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof BookableTime)){
            return false;
        }
        BookableTime bt = (BookableTime)obj;
        return bt.hour == this.hour;
    }

    @NonNull
    @Override
    public String toString() {
        return "HOUR:"+this.hour;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = new Integer(PRIME * this.hour).hashCode();
        hash += (toString().hashCode()*PRIME);
        return hash;
    }

    public int getHour(){
        return hour;
    }



}
