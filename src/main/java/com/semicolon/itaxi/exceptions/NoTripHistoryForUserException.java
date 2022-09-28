package com.semicolon.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class NoTripHistoryForUserException extends ITaxiException {
    private HttpStatus status;

    public NoTripHistoryForUserException(String message, HttpStatus status) {
        super(message, status);
    }
}
