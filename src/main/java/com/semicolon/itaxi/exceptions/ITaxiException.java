package com.semicolon.itaxi.exceptions;

public class ITaxiException extends Exception{
    private int statusCode;
    
    public ITaxiException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode(){
        return  statusCode;
    }
}

