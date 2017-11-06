package com.example.jmay.asla;


import java.sql.Connection;
import java.sql.Statement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Properties;
import java.sql.SQLException;

public class JDBCInterface extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "ASLAtables";
    private static final String ACCEL = "Accelerometer";
    private static final String X_VALUE = "X";
    private static final String Y_VALUE = "Y";
    private static final String Z_VALUE = "Z";

    public JDBCInterface(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ACCL_TABLE = "CREATE TABLE Accelerometer(X FLOAT, Y FLOAT, Z FLOAT, TIMESTAMP TEXT);";
        db.execSQL(CREATE_ACCL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Accelerometer");
        onCreate(db);
    }

    public void addData(float X, float Y, float Z, String time, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Timestamp", time);
        values.put("X", X);
        values.put("Y", Y);
        values.put("Z", Z);

        db.insert(table, null, values);
        db.close();
    }


//    public static void createDatabase() {
//        Connection connection = getConnection();
//        String create_accl = "CREATE TABLE IF NOT EXSITS `Accelerometer` (`X` FLOAT NOT NULL, " +
//                "`Y` FLOAT NOT NULL, `Z` FLOAT NOT NULL, `TIMESTAMP` VARCHAR(45) NOT NULL, PRIMARY KEY(`TIMESTAMP`));";
//        String create_gyro = "CREATE TABLE IF NOT EXISTS `Gyroscope` (`X` FLOAT NOT NULL, `Y` FLOAT NOT NULL," +
//                "`Z` FLOAT NOT NULL, `TIMESTAMP` VARCHAR(45) NOT NULL, PRIMARY KEY(`TIMESTAMP`));";
//        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
//        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, create_accl);
//        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, create_gyro);
//    }
//
//    public static Connection getConnection(){
//        Connection connection = null;
//        Properties properties = new Properties();
//        try{
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ASLAtables", properties);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static void clearDatabase(){
//        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
//        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, "truncate `Accelerometer`;");
//        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, "truncate `Gyroscope`;");
//    }
//
//    public static void addAccelerometer(Float x, Float y, Float z, long timestamp) throws SQLException{
//        String addAccl = "INSERT INTO `Accelerometer` (`X`, `Y`, `Z`, `Timestamp`) VALUES " +
//                " ('" + x + "', '" + y + "', '" + z + "', 'SELECT CONVERT(varchar, " + timestamp + ")');";
//        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
//        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, addAccl);
//    }
//
//    public static void addGyroscope(Float x, Float y, Float z, long timestamp) throws SQLException {
//        String addGyro = "INSERT INTO `Gyroscope` (`X`, `Y`, `Z`, `Timestamp`) VALUES  ('" + x + "', '" +
//                y + "', '" + z + "', 'SELECT CONVERT(varchar, " + timestamp + ")');";
//        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
//        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, addGyro);
//    }
//
//    private static class ExecuteUpdateTask extends AsyncTask<String, Object, Boolean>{
//        protected Boolean doInBackground(String... params) {
//            try {
//                Connection connection = getConnection();
//                Statement statement = connection.createStatement();
//                statement.executeUpdate(params[0]);
//                return true;
//            }catch (Exception e){
//                e.printStackTrace();
//                return false;
//            }
//
//        }
//    }
//
//    private static class GetConnectionTask extends  AsyncTask<String, Object, Connection>{
//        protected Connection doInBackground(String... params){
//            Connection connection = null;
//            Properties properties = new Properties();
//            properties.put("x", params[1]);
//            properties.put("y", params[2]);
//            properties.put("z", params[3]);
//            properties.put("timestamp", params[4]);
//
//            try{
//                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//                connection = DriverManager.getConnection(params[0], properties);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return connection;
//        }
//    }
}