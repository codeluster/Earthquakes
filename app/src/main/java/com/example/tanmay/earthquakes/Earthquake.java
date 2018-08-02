package com.example.tanmay.earthquakes;

public class Earthquake {

    private float magnitude;
    private String location;
    private long timeInMilliseconds;
    private String url;

    public Earthquake(float magnitude, String location, long timeInMilliseconds, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMilliseconds = timeInMilliseconds;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
}
