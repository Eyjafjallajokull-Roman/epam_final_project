package com.epam.project.exception;

public class NoSuchActivityException extends Exception {
    public NoSuchActivityException(){
        super("No such Activity");
    }
}
