package com.example.jmay.asla;

/**
 * Created by jmay on 10/16/2017.
 */

public interface DataLoggerInterface {
    void setHeaders(Iterable<String> headers) throws IllegalStateException;
    void addRow(Iterable<String> values) throws IllegalStateException;
    String writeToFile();

    }
