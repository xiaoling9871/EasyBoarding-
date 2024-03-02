package com.example.authserver.exception;

public class IdNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Id not exist!";
    }
}
