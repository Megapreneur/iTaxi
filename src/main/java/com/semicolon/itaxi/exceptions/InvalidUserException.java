package com.semicolon.itaxi.exceptions;

public class InvalidUserException extends IllegalArgumentException {
    public InvalidUserException(String message) {
        super(message);
    }
}
