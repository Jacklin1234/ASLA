package com.example.jmay.asla;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Connection;
import java.sql.Statement;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Properties;
import java.sql.SQLException;

public class DataLogger{

    public static void createDatabase() throws SQLException {
        Connection connection = getConnection();
        String create_accl = "CREATE TABLE IF NOT EXSITS `Accelerometer` (`X` FLOAT NOT NULL, " +
                "`Y` FLOAT NOT NULL, `Z` FLOAT NOT NULL, `TIMESTAMP` VARCHAR(45) NOT NULL, PRIMARY KEY(`TIMESTAMP`));";
        String create_gyro = "CREATE TABLE IF NOT EXISTS `Gyroscope` (`X` FLOAT NOT NULL, `Y` FLOAT NOT NULL," +
                "`Z` FLOAT NOT NULL, `TIMESTAMP` VARCHAR(45) NOT NULL, PRIMARY KEY(`TIMESTAMP`));";
        Statement statement = connection.createStatement();
        statement.executeUpdate(create_accl);
        statement.execute(create_gyro);
    }

    public static Connection getConnection(){
        Connection connection = null;
        Properties properties = new Properties();
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ASLAtables", properties);
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void clearDatabase() throws SQLException {
        Connection connection = getConnection();
        String clear_accl = "truncate `Accelerometer`;";
        String clear_gyro = "truncate `Gyroscope`;";
        Statement statement = connection.createStatement();
        statement.executeUpdate(clear_accl);
        statement.executeUpdate(clear_gyro);
    }

    public static void addAccelerometer(Float x, Float y, Float z, long timestamp) throws SQLException{
        Connection connection = getConnection();
        String addAccl = "INSERT INTO `Accelerometer` (`X`, `Y`, `Z`, `Timestamp`) VALUES " +
                " ('" + x + "', '" + y + "', '" + z + "', 'SELECT CONVERT(varchar, " + timestamp + ")');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(addAccl);
    }

    public static void addGyroscope(Float x, Float y, Float z, long timestamp) throws SQLException {
        Connection connection = getConnection();
        String addGyro = "INSERT INTO `Gyroscope` (`X`, `Y`, `Z`, `Timestamp`) VALUES  ('" + x + "', '" +
                y + "', '" + z + "', 'SELECT CONVERT(varchar, " + timestamp + ")');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(addGyro);
    }

}