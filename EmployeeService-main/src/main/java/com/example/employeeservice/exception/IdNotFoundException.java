package com.example.employeeservice.exception;

public class IdNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Id not found!";
    }
}
