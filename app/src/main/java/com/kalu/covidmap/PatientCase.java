package com.kalu.covidmap;

public class PatientCase {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private boolean drycough;
    private boolean headache;
    private boolean senselose;
    private boolean yourtemperature;
    private double latitude;
    private double longitude;

    public PatientCase() {
    }

    public boolean isDrycough() {
        return drycough;
    }

    public void setDrycough(boolean drycough) {
        this.drycough = drycough;
    }

    public boolean isHeadache() {
        return headache;
    }

    public void setHeadache(boolean headache) {
        this.headache = headache;
    }

    public boolean isSenselose() {
        return senselose;
    }

    public void setSenselose(boolean senselose) {
        this.senselose = senselose;
    }

    public boolean isYourtemperature() {
        return yourtemperature;
    }

    public void setYourtemperature(boolean yourtemperature) {
        this.yourtemperature = yourtemperature;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
