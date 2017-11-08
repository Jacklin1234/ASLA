package com.example.jmay.asla;

import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Context;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import android.widget.LinearLayout;
import java.io.FilterInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.InterruptedException;
import java.util.Objects;

import android.hardware.camera2.CameraManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.camera2.CameraAccessException;
import android.os.Handler;
import android.content.Intent;
import android.app.Activity;


public class MainActivity extends Activity implements SensorEventListener, OnClickListener{
        //buttons to start collecting data, ending data collection, and then uploading it to the server
    private Button startButton, stopButton, uploadButton;
    private SensorManager sensorManager;
    private ArrayList<Accelerometer> AccelerometerSensorRawData;
    private ArrayList<Gyroscope> GyroscopeSensorRawData;
    private ArrayList<Proximity> ProximitySensorRawData;
    private ArrayList<LightSensor> LightSensorRawData;
    private ArrayList<Accelerometer> AccelerometerSensorData;
    private ArrayList<Gyroscope> GyroscopeSensorData;
    private ArrayList<Proximity> ProximitySensorData;
    private ArrayList<LightSensor> LightSensorData;
    private boolean hasStarted = false;
    private LinearLayout linearLayout;
    private long StartTime;
    private long EndTime;
    //private DataLogger db;
    private MyDatabase db;
    private String ServersURL = "http://cse.unl.edu.com/THINKLab/ASLA.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        startButton.setEnabled(true);

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
        stopButton.setEnabled(false);

        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(this);
        uploadButton.setEnabled(false);




        //linearLayout = (LinearLayout) findViewById(R.id.chart_container);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected  void onPause(){
        super.onPause();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;
        if(hasStarted){
            if(sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];
                long timestamp = System.currentTimeMillis();
                Accelerometer data = new Accelerometer(timestamp, x, y, z);
                AccelerometerSensorRawData.add(data);
                Log.d("Accelerometer Data: ", data.toString());
            }
            if(sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                double x = event.values[0];
                double y = event.values[1];
                double z = event.values[2];
                long timestamp = System.currentTimeMillis();
                Gyroscope data = new Gyroscope(timestamp, x, y, z);
                GyroscopeSensorRawData.add(data);
                Log.d("Gyroscope Data: ", data.toString());
            }
            if(sensor.getType() == Sensor.TYPE_PROXIMITY)
            {
                double distance = event.values[0];
                long timestamp = System.currentTimeMillis();
                Proximity data = new Proximity(timestamp,distance);
                ProximitySensorRawData.add(data);
                Log.d("Proximity Data", data.toString());
            }
            if(sensor.getType() == Sensor.TYPE_LIGHT)
            {
                double Illuminance = event.values[0];
                long timestamp = System.currentTimeMillis();
                LightSensor data = new LightSensor(timestamp,Illuminance);
                LightSensorRawData.add(data);
                Log.d("Light Data", data.toString());
            }
            /*
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
            */

            /*
            if(isCameraUsebyApp()){
                Log.d("Camera Status:", "In use");
            }
            */

        }
    }

    //registers when buttons are clicked and what should happen as a result
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                StartTime = System.currentTimeMillis();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                uploadButton.setEnabled(false);
                AccelerometerSensorRawData = new ArrayList<Accelerometer>();
                GyroscopeSensorRawData = new ArrayList<Gyroscope>();
                ProximitySensorRawData = new ArrayList<Proximity>();
                LightSensorRawData = new ArrayList<LightSensor>();
                db = new MyDatabase();

                hasStarted = true;
                Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
                sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
                sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_FASTEST);
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
               // Log.d("button: ","button clicke   d");
                break;
            case R.id.stopButton:
                EndTime = System.currentTimeMillis();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                uploadButton.setEnabled(true);
                sensorManager.unregisterListener(this);

                /*
                db.Test();
                if(AccelerometerSensorRawData.size()!=0)
                    db.addData(AccelerometerSensorRawData.get(0));
                if(GyroscopeSensorRawData.size()!=0)
                  db.addData(GyroscopeSensorRawData.get(0));
                if(ProximitySensorRawData.size()!= 0)
                    db.addData(ProximitySensorRawData.get(0));
                if(LightSensorRawData.size()!=0)
                    db.addData(LightSensorRawData.get(0));
                */
                Log.d("TAG: ","StopButton");
                Log.d("AccelerometerRawData", String.valueOf(AccelerometerSensorRawData.size()));
                Log.d("GyroscopeRawData", String.valueOf(GyroscopeSensorRawData.size()));
                Log.d("ProximityRawData", String.valueOf(ProximitySensorRawData.size()));
                Log.d("LightRawData", String.valueOf(LightSensorRawData.size()));

                if(AccelerometerSensorRawData.size()!=0) {
                    AccelerometerSensorData = HandleAccelerometerRawData(AccelerometerSensorRawData, StartTime, EndTime);
                    Log.d("AccelerometerSensorData", String.valueOf(AccelerometerSensorData.size()));
                    new MyDatabase().execute(AccelerometerSensorData.toArray(new Accelerometer[AccelerometerSensorData.size()]));

                }
                if(GyroscopeSensorRawData.size()!=0){
                    GyroscopeSensorData = HandleGyroscopeRawData(GyroscopeSensorRawData, StartTime, EndTime);
                    Log.d("GyroscopeData", String.valueOf(GyroscopeSensorData.size()));
                    new MyDatabase().execute(GyroscopeSensorData.toArray(new Gyroscope[GyroscopeSensorData.size()]));
                }
                if(ProximitySensorRawData.size()!= 0){
                    ProximitySensorData = HandleProximityRawData(ProximitySensorRawData, StartTime, EndTime);
                    Log.d("ProximityData", String.valueOf(ProximitySensorData.size()));
                    new MyDatabase().execute(ProximitySensorData.toArray(new Proximity[ProximitySensorData.size()]));
                }
                if(LightSensorRawData.size()!=0){
                    LightSensorData = HandleLightSensorRawData(LightSensorRawData, StartTime, EndTime);
                    Log.d("LightSensorData", String.valueOf(LightSensorData.size()));
                    new MyDatabase().execute(LightSensorData.toArray(new LightSensor[LightSensorData.size()]));
                }

                break;
            default:
                break;
        }
    }
    public boolean isCameraUsebyApp() {
        Camera camera = null;
        try {
            camera = Camera.open(0);
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (camera != null) camera.release();
        }
        return false;
    }

    public ArrayList<Accelerometer> HandleAccelerometerRawData(ArrayList<Accelerometer> AccelerometerSensorRawData, long startTime, long endTime){
        long preTime = startTime;
        int index = 1;
        ArrayList<Accelerometer> AccelerometerSensorData = new ArrayList<Accelerometer>();
        AccelerometerSensorData.add(new Accelerometer(startTime,
                                                      AccelerometerSensorRawData.get(0).getX(),
                                                      AccelerometerSensorRawData.get(0).getY(),
                                                      AccelerometerSensorRawData.get(0).getZ()));
        for(preTime = startTime; preTime < endTime; preTime += 1000){
            for(int i = index; i < AccelerometerSensorRawData.size(); i++){
                if(AccelerometerSensorRawData.get(i).getTimestamp() > preTime) {
                    Accelerometer acc = new Accelerometer(preTime,
                                                          AccelerometerSensorRawData.get(i - 1).getX(),
                                                          AccelerometerSensorRawData.get(i - 1).getY(),
                                                          AccelerometerSensorRawData.get(i - 1).getZ());
                    AccelerometerSensorData.add(acc);
                    index = i-1;
                    break;
                }
            }
        }
        return AccelerometerSensorData;
    }

    public ArrayList<Gyroscope> HandleGyroscopeRawData(ArrayList<Gyroscope> GyroscopeSensorRawData, long startTime, long endTime){
        long preTime = startTime;
        int index = 1;
        ArrayList<Gyroscope> GySensorSensorData = new ArrayList<Gyroscope>();
        GySensorSensorData.add(new Gyroscope(startTime,
                                             GyroscopeSensorRawData.get(0).getRx(),
                                             GyroscopeSensorRawData.get(0).getRy(),
                                             GyroscopeSensorRawData.get(0).getRz()));
        for(preTime = startTime; preTime < endTime; preTime += 1000){
            for(int i = index; i < GyroscopeSensorRawData.size(); i++){
                if(GyroscopeSensorRawData.get(i).getTimestamp() > preTime) {
                    Gyroscope gyr = new Gyroscope(preTime,
                            GyroscopeSensorRawData.get(i - 1).getRx(),
                            GyroscopeSensorRawData.get(i - 1).getRy(),
                            GyroscopeSensorRawData.get(i - 1).getRz());
                    GySensorSensorData.add(gyr);
                    index = i-1;
                    break;
                }
            }
        }
        return GySensorSensorData;
    }

    public ArrayList<Proximity> HandleProximityRawData(ArrayList<Proximity> ProximitySensorRawData, long startTime, long endTime){
        long preTime = startTime;
        int index = 1;
        ArrayList<Proximity> ProximitySensorData = new ArrayList<Proximity>();
        ProximitySensorData.add(new Proximity(startTime,
                                              ProximitySensorRawData.get(0).getDistance()));
        for(preTime = startTime; preTime < endTime; preTime += 1000){
            for(int i = index; i < ProximitySensorRawData.size(); i++){
                if(ProximitySensorRawData.get(i).getTimestamp() > preTime) {
                    Proximity prox = new Proximity(preTime,
                                                   ProximitySensorRawData.get(i - 1).getDistance());
                    ProximitySensorData.add(prox);
                    break;
                }
                else if(i == ProximitySensorRawData.size() - 1 )
                {
                    Proximity prox = new Proximity(preTime, ProximitySensorRawData.get(i).getDistance());
                    ProximitySensorData.add(prox);
                }
            }
        }
        return ProximitySensorData;
    }

    public ArrayList<LightSensor> HandleLightSensorRawData(ArrayList<LightSensor> LightSensorRawData, long startTime, long endTime){
        long preTime = startTime;
        int index = 1;
        ArrayList<LightSensor> LightSensorData = new ArrayList<LightSensor>();
        LightSensorData.add(new LightSensor(startTime,
                                            LightSensorRawData.get(0).getIlluminance()));
        for(preTime = startTime; preTime < endTime; preTime += 1000){
            for(int i = index; i < LightSensorRawData.size(); i++){
                if(LightSensorRawData.get(i).getTimestamp() > preTime) {
                    LightSensor light = new LightSensor(preTime, LightSensorRawData.get(i - 1).getIlluminance());
                    LightSensorData.add(light);

                    break;
                }
                else if(i == LightSensorRawData.size() - 1 )
                {
                    LightSensor light = new LightSensor(preTime, LightSensorRawData.get(i).getIlluminance());
                    LightSensorData.add(light);
                }
            }
        }
        return LightSensorData;
    }

}
