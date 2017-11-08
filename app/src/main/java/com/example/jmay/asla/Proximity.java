package com.example.jmay.asla;

/**
 * Created by qlin on 10/30/2017.
 */

public class Proximity {
    private long timestamp;
    private double distance;


    public Proximity(long timestamp, double distance) {
        this.timestamp = timestamp;
        this.distance = distance;

    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Proximity{" +
                "timestamp=" + timestamp +
                ", distance=" + distance +
                '}';
    }
}