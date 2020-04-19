package com.example.footballfieldmanager.controller.user;

import com.example.footballfieldmanager.model.User;

import java.util.List;

public interface UserDelegate {

    void userCorrectlyDownloaded(List<User> userList);

    void userOperationFailed(Exception exc);
}
