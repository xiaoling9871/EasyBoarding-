package com.example.authserver.exception;

public class EmailNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Email not exist!";
    }
}
