package com.example.jmay.asla;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DataLogger extends SQLiteOpenHelper{

    private static final String acclTable = "Accelerometer";
    private static final String CREATE_TABLE_ACC = "CREATE TABLE " + acclTable +
            "(id INTEGER PRIMARY KEY, Accelerometer TEXT, status INTEGER, created_at DATETIME)";

    public DataLogger(Context context){
        super(context, "Sensor Data", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_ACC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int older, int newer){
        db.execSQL("DROP TABLE IF EXISTS " + acclTable);

        onCreate(db);
    }
}