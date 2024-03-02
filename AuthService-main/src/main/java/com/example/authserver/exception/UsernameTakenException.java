package com.example.authserver.exception;

public class UsernameTakenException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Username has already been taken!";
    }
}
