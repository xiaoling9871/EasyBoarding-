package com.example.employeeservice.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class EmployeeSummaryDto {
    String fullName;
    String phoneNumber;
    String email;
}
