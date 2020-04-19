package com.example.footballfieldmanager.model;

import androidx.annotation.NonNull;

public class FootballField {
    private int id;

    private int numberPlayers;

    private boolean isIndoor;

    private String pitchType;

    private FootballField(){

    }

    public static class Builder{
        private int id;

        private int numberPlayers;

        private boolean isIndoor;

        private String pitchType;

        public int getId() {
            return id;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public int getNumberPlayers() {
            return numberPlayers;
        }

        public Builder setNumberPlayers(int numberPlayers) {
            this.numberPlayers = numberPlayers;
            return this;
        }

        public boolean isIndoor() {
            return isIndoor;
        }

        public Builder setIndoor(boolean indoor) {
            isIndoor = indoor;
            return this;
        }

        public String getPitchType() {
            return pitchType;
        }

        public Builder setPitchType(String pitchType) {
            this.pitchType = pitchType;
            return this;
        }

        public FootballField build(){
            FootballField footballField = new FootballField();
            footballField.setId(id);
            footballField.setIndoor(isIndoor);
            footballField.setPitchType(pitchType);
            footballField.setNumberPlayers(numberPlayers);
            return footballField;
        }
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberPlayers() {
        return numberPlayers;
    }

    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public boolean isIndoor() {
        return isIndoor;
    }

    public void setIndoor(boolean indoor) {
        isIndoor = indoor;
    }

    public String getPitchType() {
        return pitchType;
    }

    public void setPitchType(String pitchType) {
        this.pitchType = pitchType;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id : " + id + " - " + "is_indoor : " + isIndoor + " - pitch type : " + pitchType;
    }
}
