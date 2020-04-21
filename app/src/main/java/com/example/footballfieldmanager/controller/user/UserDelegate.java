package com.example.footballfieldmanager.controller.user;

import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.User;

import java.util.List;

public interface UserDelegate {

    void userCorrectlyDownloaded(List<User> userList);

    void userFutureRentsDownloaded(List<FootballFieldRent> rents);

    void userOperationFailed(Exception exc);
}
