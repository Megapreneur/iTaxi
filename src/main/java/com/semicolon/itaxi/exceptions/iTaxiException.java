package com.semicolon.itaxi.exceptions;

public class iTaxiException extends Exception{
    private int statusCode;
    
    public iTaxiException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode(){
        return  statusCode;
    }
}
