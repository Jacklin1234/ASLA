package com.example.jmay.asla;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlin on 11/6/2017.
 */

public class MyDatabase extends AsyncTask<Object, Void, Integer> {

    @Override
    protected Integer doInBackground(Object... objects) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            System.out.println("InstantiationException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //Step 2: instantiate the database connection
            conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
            //Step 3-4: formulate, prepare, and execute a query


            if(objects[0].getClass().getName().equals("com.example.jmay.asla.Accelerometer")) {
                //String InsertQuery = "use Sensor_Data;";
                String InsertQuery = "use Sensor_Data; Insert Into Accelerometer (Timestamp, X, Y, Z) values(?,?,?,?)";

                for (int i = 1; i < objects.length; i++)
                    if (i != objects.length - 1)
                        InsertQuery += ", (?,?,?,?)";
                    else
                        InsertQuery += ", (?,?,?,?);";

                ps = conn.prepareStatement(InsertQuery);
                for (int i = 0; i < objects.length; i++) {
                    ps.setString(i * 4 + 1, String.valueOf(((Accelerometer) objects[i]).getTimestamp()));
                    ps.setString(i * 4 + 2, String.valueOf(((Accelerometer) objects[i]).getX()));
                    ps.setString(i * 4 + 3, String.valueOf(((Accelerometer) objects[i]).getY()));
                    ps.setString(i * 4 + 4, String.valueOf(((Accelerometer) objects[i]).getZ()));

                }

                ps.executeUpdate();

            }

            if(objects[0].getClass().getName().equals("com.example.jmay.asla.Gyroscope"))
            {
                String InsertQuery = "Use Sensor_Data;";
                InsertQuery += "Insert Into Gyroscope (Timestamp, Rx, Ry, Rz) values(?,?,?,?)";

                for(int i = 1; i < objects.length; i++)
                    if(i != objects.length-1)
                        InsertQuery += ", (?,?,?,?)";
                    else
                        InsertQuery += ", (?,?,?,?);";

                ps = conn.prepareStatement(InsertQuery);
                for(int i =0; i < objects.length; i++) {
                    ps.setString(i*4 + 1, String.valueOf(((Gyroscope)objects[i]).getTimestamp()));
                    ps.setString(i*4 + 2, String.valueOf(((Gyroscope)objects[i]).getRx()));
                    ps.setString(i*4 + 3, String.valueOf(((Gyroscope)objects[i]).getRy()));
                    ps.setString(i*4 + 4, String.valueOf(((Gyroscope)objects[i]).getRz()));

                }
                ps.executeUpdate();

            }
            if(objects[0].getClass().getName().equals("com.example.jmay.asla.Proximity")){
                String InsertQuery = "Use Sensor_Data;";
                InsertQuery += "Insert Into Proximity (Timestamp, Distance) values(?,?)";

                for(int i = 1; i < objects.length; i++)
                    if(i != objects.length-1)
                        InsertQuery += ", (?,?)";
                    else
                        InsertQuery += ", (?,?);";

                ps = conn.prepareStatement(InsertQuery);
                for(int i =0; i < objects.length; i++) {
                    ps.setString(i * 2 + 1, String.valueOf(((Proximity) objects[i]).getTimestamp()));
                    ps.setString(i * 2 + 2, String.valueOf(((Proximity) objects[i]).getDistance()));
                }
                ps.executeUpdate();
            }

            if(objects[0].getClass().getName().equals("com.example.jmay.asla.LightSensor"))
            {
                String InsertQuery = "Use Sensor_Data;";
                InsertQuery += "Insert Into LightSensor (Timestamp, Illuminance) values(?,?)";

                for(int i = 1; i < objects.length; i++)
                    if(i != objects.length-1)
                        InsertQuery += ", (?,?)";
                    else
                        InsertQuery += ", (?,?);";

                ps = conn.prepareStatement(InsertQuery);
                for(int i =0; i < objects.length; i++) {
                    ps.setString(i*2 + 1, String.valueOf(((LightSensor)objects[i]).getTimestamp()));
                    ps.setString(i*2 + 2, String.valueOf(((LightSensor)objects[i]).getIlluminance()));
                }
                ps.executeUpdate();
            }


            } catch(Exception e) {
                System.out.println("SQLException: ");
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //Step 5: Close your resources!
                try {
                    if(rs != null /*&& !rs.isClosed()*/)
                        rs.close();
                    if(ps != null /*&& !ps.isClosed()*/)
                        ps.close();
                    if(conn != null && !conn.isClosed())
                        conn.close();
                    return 1;
                } catch (SQLException e) {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
    }
    protected void onPostExecute(Integer result) {
        if(result == 1)
            Log.d("Result: ", "Finished!: ");;
    }

}
