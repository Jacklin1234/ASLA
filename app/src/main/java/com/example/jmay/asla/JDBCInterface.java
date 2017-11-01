package com.example.jmay.asla;


import java.sql.Connection;
import java.sql.Statement;
import android.os.AsyncTask;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.SQLException;

public class JDBCInterface{

    public static void createDatabase() {
        Connection connection = getConnection();
        String create_accl = "CREATE TABLE IF NOT EXSITS `Accelerometer` (`X` FLOAT NOT NULL, " +
                "`Y` FLOAT NOT NULL, `Z` FLOAT NOT NULL, `TIMESTAMP` VARCHAR(45) NOT NULL, PRIMARY KEY(`TIMESTAMP`));";
        String create_gyro = "CREATE TABLE IF NOT EXISTS `Gyroscope` (`X` FLOAT NOT NULL, `Y` FLOAT NOT NULL," +
                "`Z` FLOAT NOT NULL, `TIMESTAMP` VARCHAR(45) NOT NULL, PRIMARY KEY(`TIMESTAMP`));";
        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, create_accl);
        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, create_gyro);
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

    public static void clearDatabase(){
        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, "truncate `Accelerometer`;");
        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, "truncate `Gyroscope`;");
    }

    public static void addAccelerometer(Float x, Float y, Float z, long timestamp) throws SQLException{
        String addAccl = "INSERT INTO `Accelerometer` (`X`, `Y`, `Z`, `Timestamp`) VALUES " +
                " ('" + x + "', '" + y + "', '" + z + "', 'SELECT CONVERT(varchar, " + timestamp + ")');";
        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, addAccl);
    }

    public static void addGyroscope(Float x, Float y, Float z, long timestamp) throws SQLException {
        String addGyro = "INSERT INTO `Gyroscope` (`X`, `Y`, `Z`, `Timestamp`) VALUES  ('" + x + "', '" +
                y + "', '" + z + "', 'SELECT CONVERT(varchar, " + timestamp + ")');";
        ExecuteUpdateTask executeUpdateTask = new ExecuteUpdateTask();
        executeUpdateTask.executeOnExecutor(ExecuteUpdateTask.THREAD_POOL_EXECUTOR, addGyro);
    }

    private static class ExecuteUpdateTask extends AsyncTask<String, Object, Boolean>{
        protected Boolean doInBackground(String... params) {
            try {
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate(params[0]);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
    }

    private static class GetConnectionTask extends  AsyncTask<String, Object, Connection>{
        protected Connection doInBackground(String... params){
            Connection connection = null;
            Properties properties = new Properties();
            properties.put("x", params[1]);
            properties.put("y", params[2]);
            properties.put("z", params[3]);
            properties.put("timestamp", params[4]);

            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                connection = DriverManager.getConnection(params[0], properties);
            }catch (Exception e){
                e.printStackTrace();
            }
            return connection;
        }
    }
}