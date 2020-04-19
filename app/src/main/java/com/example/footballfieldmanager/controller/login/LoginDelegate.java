package com.example.footballfieldmanager.controller.login;

public interface LoginDelegate {

    void isLoginSuccessfullyCompleted(String token, String userId);

    void hasLoginFailed(Exception exc);

}
