package com.example.jmay.asla;

/**
 * Created by qlin on 10/31/2017.
 */

public class LightSensor {
    private long timestamp;
    private double Illuminance;


    public LightSensor(long timestamp, double illuminance) {
        this.timestamp = timestamp;
        Illuminance = illuminance;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getIlluminance() {
        return Illuminance;
    }

    public void setIlluminance(double illuminance) {
        Illuminance = illuminance;
    }

    @Override
    public String toString() {
        return "LightSensor{" +
                "timestamp=" + timestamp +
                ", Illuminance=" + Illuminance +
                '}';
    }
}