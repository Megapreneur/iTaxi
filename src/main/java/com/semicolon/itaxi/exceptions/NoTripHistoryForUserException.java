package com.semicolon.itaxi.exceptions;


public class NoTripHistoryForUserException extends ITaxiException {

    public NoTripHistoryForUserException(String message) {
        super(message);
    }
}
