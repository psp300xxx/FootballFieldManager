package com.example.footballfieldmanager.controller.user;

import com.android.volley.RequestQueue;

public interface UserIF {

    void setDelegate(UserDelegate delegate);

    UserDelegate getDelegate();

    void searchFriends(String name, RequestQueue queue);

    void searchUserFutureRents(String userID, RequestQueue queue);
}
