package com.example.mongodb.entity.Response;

import com.example.mongodb.entity.EmployeeEntity.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ResponseEmployeeApplication {
    private String status;
    private Employee employee;
}