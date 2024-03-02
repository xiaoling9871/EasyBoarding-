package com.bfs.springdatademo.entity.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ResponseRegistrationToken {

    private String message;
    private Object data;
}
