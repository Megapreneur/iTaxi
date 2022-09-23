package com.semicolon.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class NoDriverFoundException extends ITaxiException{
    public NoDriverFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
