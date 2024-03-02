package com.example.authserver.exception;

public class EmailTakenException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Email has already been taken!";
    }
}
