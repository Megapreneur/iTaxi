package com.semicolon.itaxi.exceptions;

import org.springframework.http.HttpStatus;

public class MismatchedPasswordException extends ITaxiException {
        public MismatchedPasswordException(String message, HttpStatus status) {
        super(message, status);
    }
}
