package com.example.footballfieldmanager.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FootballFieldRent {

    public static class Builder{

        private int id;

        private List<String> userIdList;

        private Date date;

        private int fieldId;

        Builder(){

        }

        public int getId() {
            return id;
        }

        public int getFieldId(){
            return fieldId;
        }

        public Builder setFieldId(int fieldId){
            this.fieldId=fieldId;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public List<String> getUserIdList() {
            return userIdList;
        }

        public Builder setUserIdList(List<String> userIdList) {
            this.userIdList = userIdList;
            return this;
        }

        public void addUserId(String userId){
            if(userIdList==null){
                userIdList = new ArrayList<>();
            }
            userIdList.add(userId);
        }

        public Date getDate() {
            return date;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public FootballFieldRent build(){
            FootballFieldRent footballFieldRent = new FootballFieldRent();
            footballFieldRent.setDate(date);
            footballFieldRent.setId(id);
            footballFieldRent.setFieldId(fieldId);
            footballFieldRent.setUserIdList(userIdList);
            return footballFieldRent;
        }
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    private int id;

    private List<String> userIdList;

    private Date date;

    private int fieldId;

    private FootballFieldRent(){

    }

    public void setFieldId(int fieldId){
        this.fieldId = fieldId;
    }

    public int getFieldId(){
        return fieldId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addUserId(String userId){
        if(userIdList==null){
            userIdList = new ArrayList<>();
        }
        userIdList.add(userId);
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
