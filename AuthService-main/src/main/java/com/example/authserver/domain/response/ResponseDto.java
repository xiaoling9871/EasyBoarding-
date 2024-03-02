package com.example.authserver.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
public class ResponseDto {
    private String message;
    private Object data;
}
