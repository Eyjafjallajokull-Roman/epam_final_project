package com.epam.project.exception;

public class DataBaseConnectionException extends Exception {
    public DataBaseConnectionException() {
        super("Unable to connect to database");
    }

}
