package com.semicolon.itaxi.exceptions;

public class IncorrectPasswordException extends IllegalArgumentException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
