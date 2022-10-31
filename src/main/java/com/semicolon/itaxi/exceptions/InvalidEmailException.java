package com.semicolon.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidEmailException extends ITaxiException {
    public InvalidEmailException(String message, HttpStatus status) {
        super(message, status);
    }
}
