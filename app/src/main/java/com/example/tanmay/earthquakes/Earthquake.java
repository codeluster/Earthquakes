package com.example.tanmay.earthquakes;

public class Earthquake {

    private String magnitude;
    private String location;
    private String time;

    public Earthquake(String magnitude, String location, String time) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
    }

    public String getLocation() {
        return this.location;
    }

    public String getTime() {
        return this.time;
    }

    public String getMagnitude() {
        return this.magnitude;
    }

}
