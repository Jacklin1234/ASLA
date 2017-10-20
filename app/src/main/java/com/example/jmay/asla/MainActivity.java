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
    private String ServersURL = "http://cse.unl.edu.com/THINKLab/ASLA.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public int uploadData(final String filePath, ArrayList data) throws MalformedURLException {
        int serverResponse = 0;

        HttpURLConnection connection;
        DataOutputStream outputStream = null;
        File chosenFile = new File(filePath);
        String[] fileNameParts = filePath.split("/");
        String fileName = fileNameParts[fileNameParts.length-1];
        String boundary = "*****";

        if(!chosenFile.isFile()){
            return 0;
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(chosenFile);
                URL url = new URL(ServersURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", filePath);

                outputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + filePath + "\"" +"\r\n");
            }catch(FileNotFoundException e){

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
