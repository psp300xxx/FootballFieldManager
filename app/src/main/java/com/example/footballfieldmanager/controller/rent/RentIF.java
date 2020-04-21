package com.example.footballfieldmanager.controller.rent;


import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.model.FootballFieldRent;

public interface RentIF {
    void setDelegate(RentDelegate delegate);

    RentDelegate getDelegate();

    void searchFutureRents(int fieldId, RequestQueue queue);

    void bookField(FootballFieldRent fieldRent,int centerId, String token, RequestQueue queue);

    void addIntoRent(FootballFieldRent fieldRent, String userId, String token, int fieldID, RequestQueue queue);
}
