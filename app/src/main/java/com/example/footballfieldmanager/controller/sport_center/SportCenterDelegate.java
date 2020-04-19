package com.example.footballfieldmanager.controller.sport_center;

import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.SportCenter;

import java.util.List;

public interface SportCenterDelegate {

    void sportCenterDownloaded(List<SportCenter> centers);

    void sportCenterDownloadFailed(Exception exc);

    void footballFieldDownloaded(List<FootballField> footballFields);

}
