package com.example.employeeservice.aop;

import com.example.employeeservice.domain.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto handleInvalidArgument(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseDto.builder()
                .message("Validation fail!")
                .data(errorMap)
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseDto handleOtherException(Exception ex) {
        return ResponseDto.builder()
                .message(ex.getMessage())
                .build();
    }

}
