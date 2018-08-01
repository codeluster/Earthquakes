package com.example.tanmay.earthquakes;

public class Earthquake {

    private String magnitude;
    private String location;
    private long timeInMilliseconds;

    public Earthquake(String magnitude, String location, long timeInMilliseconds) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMilliseconds = timeInMilliseconds;
    }

    public String getLocation() {
        return this.location;
    }

    public long getTimeInMilliseconds() {
        return this.timeInMilliseconds;
    }

    public String getMagnitude() {
        return this.magnitude;
    }

}
