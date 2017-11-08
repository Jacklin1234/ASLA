package com.example.jmay.asla;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import java.sql.Timestamp;

public class DataLogger extends SQLiteOpenHelper{

    private static final String acclTable = "Accelerometer";
    private static final String GyroTable = "Gyroscope";
    private static final String ProxTable = "Proximity";
    private static final String LightTable = "LightSensor";
    private static final String CREATE_TABLE_ACC = "CREATE TABLE " + acclTable +
            "(id INTEGER PRIMARY KEY, Timestamp INTEGER, X REAL, Y REAL, Z REAL, status INTEGER);";
    private static final String CREATE_TABLE_GYRO = "CREATE TABLE " + GyroTable +
            "(id INTEGER PRIMARY KEY, Timestamp INTEGER, X REAL, Y REAL, Z REAL, status INTEGER);";
    private static final String CREATE_TABLE_PROX = "CREATE TABLE " + ProxTable +
            "(id INTEGER PRIMARY KEY, Timestamp INTEGER, Distance REAL, status INTEGER);";
    private static final String CREATE_TABLE_LIGHT = "CREATE TABLE " + LightTable +
            "(id INTEGER PRIMARY KEY, Timestamp INTEGER, Illuminance REAL, status INTEGER);";

    public DataLogger(Context context){
        super(context, "Sensor_Data", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        /*
        db.execSQL("DROP TABLE IF EXISTS " + acclTable + ";" + CREATE_TABLE_ACC  +
                   "DROP TABLE IF EXISTS " + GyroTable + ";" + CREATE_TABLE_GYRO +
                   "DROP TABLE IF EXISTS " + ProxTable + ";" + CREATE_TABLE_PROX +
                   "DROP TABLE IF EXISTS " + LightTable + ";" + CREATE_TABLE_LIGHT );
        */
        db.execSQL("DROP TABLE IF EXISTS " + acclTable);
        db.execSQL(CREATE_TABLE_ACC);
        db.execSQL("DROP TABLE IF EXISTS " + GyroTable);
        db.execSQL(CREATE_TABLE_GYRO);
        db.execSQL("DROP TABLE IF EXISTS " + ProxTable);
        db.execSQL(CREATE_TABLE_PROX);
        db.execSQL("DROP TABLE IF EXISTS " + LightTable);
        db.execSQL(CREATE_TABLE_LIGHT);

        Log.d("DB: ","Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int older, int newer){
       // db.execSQL("DROP TABLE IF EXISTS " + acclTable);

        db.execSQL("DROP TABLE IF EXISTS " + acclTable);
        db.execSQL(CREATE_TABLE_ACC);
        db.execSQL("DROP TABLE IF EXISTS " + GyroTable);
        db.execSQL(CREATE_TABLE_GYRO);
        db.execSQL("DROP TABLE IF EXISTS " + ProxTable);
        db.execSQL(CREATE_TABLE_PROX);
        db.execSQL("DROP TABLE IF EXISTS " + LightTable);
        db.execSQL(CREATE_TABLE_LIGHT);

        Log.d("OnUpgrade", " Updated");

    }

    public void Test(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,0,1);
    }
    public void addData(Accelerometer accelerometer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Timestamp",accelerometer.getTimestamp());
        values.put("X",accelerometer.getX());
        values.put("Y",accelerometer.getY());
        values.put("Z",accelerometer.getZ());
        values.put("status",1);
        db.insert(acclTable,null,values);
        db.close();
    }

    public void addData(Gyroscope gyroscope){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Timestamp",gyroscope.getTimestamp());
        values.put("X",gyroscope.getRx());
        values.put("Y",gyroscope.getRy());
        values.put("Z",gyroscope.getRz());
        values.put("status",1);
        db.insert(GyroTable,null,values);
        db.close();
    }
    public void addData(Proximity proximity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Timestamp",proximity.getTimestamp());
        values.put("Distance",proximity.getDistance());
        values.put("status",1);
        db.insert(ProxTable,null,values);
        db.close();
    }
    public void addData(LightSensor lightSensor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Timestamp",lightSensor.getTimestamp());
        values.put("Illuminance",lightSensor.getIlluminance());
        values.put("status",1);
        db.insert(LightTable,null,values);
        db.close();
    }
}