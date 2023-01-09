package com.semicolon.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class ITaxiException extends Exception{
//    private HttpStatus status;
    
    public ITaxiException(String message){
        super(message);
//        this.status = status;
    }

}

