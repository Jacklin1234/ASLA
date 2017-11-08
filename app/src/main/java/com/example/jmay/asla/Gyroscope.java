package com.example.jmay.asla;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by jmay on 10/15/2017.
 */

public class Gyroscope {
    private long timestamp;
    private double Rx; //coordinates for accelerometer
    private double Ry;
    private double Rz;

    public Gyroscope(long timestamp, double rx, double ry, double rz) {
        this.timestamp = timestamp;
        Rx = rx;
        Ry = ry;
        Rz = rz;

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getRx() {
        return Rx;
    }

    public void setRx(double rx) {
        Rx = rx;
    }

    public double getRy() {
        return Ry;
    }

    public void setRy(double ry) {
        Ry = ry;
    }

    public double getRz() {
        return Rz;
    }

    public void setRz(double rz) {
        Rz = rz;
    }

    @Override
    public String toString() {
        return "Gyroscope{" +
                "timestamp=" + timestamp +
                ", Rx=" + Rx +
                ", Ry=" + Ry +
                ", Rz=" + Rz +
                '}';
    }
}