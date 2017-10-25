package com.example.jmay.asla;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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

public class MainActivity extends Activity implements SensorEventListener, OnClickListener{
        //buttons to start collecting data, ending data collection, and then uploading it to the server
    private Button startButton, stopButton, uploadButton;
    private SensorManager sensorManager;
    private ArrayList sensorData;
    private boolean hasStarted = false;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorData = new ArrayList();

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
        System.out.println("2");
        if(hasStarted){
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            long timestamp = System.currentTimeMillis();
            Accelerometer data = new Accelerometer(timestamp, x, y, z);
            sensorData.add(data);
        }
    }



    //registers when buttons are clicked and what should happen as a result
    @Override
    public void onClick(View v) {
        System.out.println("3");
        switch (v.getId()) {
            case R.id.startButton:
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                uploadButton.setEnabled(false);
                sensorData = new ArrayList();
                hasStarted = true;
                Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
                break;
            case R.id.stopButton:
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                uploadButton.setEnabled(true);
                sensorManager.unregisterListener(this);
                break;
            default:
                break;
        }
    }

}
