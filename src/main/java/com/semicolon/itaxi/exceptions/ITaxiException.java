package com.semicolon.itaxi.exceptions;

public class ITaxiException extends Exception{
    private int statusCode;
    
    public ITaxiException(String message){
        super(message);
    }

}

