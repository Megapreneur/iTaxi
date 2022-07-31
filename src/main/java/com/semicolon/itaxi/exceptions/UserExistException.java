package com.semicolon.itaxi.exceptions;

public class UserExistException extends IllegalArgumentException{
    public UserExistException(String message) {
        super(message);
    }
}
