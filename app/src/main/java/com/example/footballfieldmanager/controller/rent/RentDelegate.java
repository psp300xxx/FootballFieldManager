package com.example.footballfieldmanager.controller.rent;

import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.FootballFieldRent;

import java.util.List;

public interface RentDelegate {

    void futureRentDownloaded(List<FootballFieldRent> list);

    void downloadFailed(Exception exc);

    void fieldbookedCorrectly();


}
