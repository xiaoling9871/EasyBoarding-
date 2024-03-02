package com.example.demo.Domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response {
    private boolean Flag;
    private String Info;
}
