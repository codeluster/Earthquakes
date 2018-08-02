package com.example.tanmay.earthquakes;

public class Earthquake {

    private float magnitude;
    private String location;
    private long timeInMilliseconds;

    public Earthquake(float magnitude, String location, long timeInMilliseconds) {
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

    public float getMagnitude() {
        return this.magnitude;
    }

}
