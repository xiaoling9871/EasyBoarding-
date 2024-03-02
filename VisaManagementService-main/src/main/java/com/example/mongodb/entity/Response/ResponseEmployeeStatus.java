package com.example.mongodb.entity.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ResponseEmployeeStatus {
    private String employeeID;
    private String status;
    private String comment;
}

