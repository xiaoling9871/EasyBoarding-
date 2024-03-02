package com.example.authserver.aop;

import com.example.authserver.domain.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseDto badCredentialException(BadCredentialsException ex) {
        return ResponseDto.builder()
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseDto userNameNotFoundException(UsernameNotFoundException ex) {
        return ResponseDto.builder()
                .message(ex.getMessage())
                .build();
    }

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
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDto handleOtherException(Exception ex) {
        return ResponseDto.builder()
                .message(ex.getMessage())
                .build();
    }
}
