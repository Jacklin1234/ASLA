package com.example.jmay.asla;

/**
 * Created by jmay on 10/2/2017.
 */

public class Accelerometer {
    private long timestamp;
    private double x; //coordinates for accelerometer
    private double y;
    private double z;

    public Accelerometer(long timestamp, double x, double y, double z){
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    //prints all the information
    public String toString(){
        return "timestamp= " + timestamp +";  x= " + x +";  y= " + y + ";  z= " + z;
    }
}

