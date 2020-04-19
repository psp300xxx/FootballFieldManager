package com.example.footballfieldmanager.model;

public class User {

    private String id;
    private String username;

    private User(){

    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private String id;
        private String username;

        Builder(){

        }

        public String getId() {
            return id;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public User build(){
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            return user;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
