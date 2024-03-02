package com.example.employeeservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Document
@Data
@Builder
public class VisaStatus {
    @Id
    private String id;

    @NotBlank(message = "Please enter the type of visa!")
    private String visaType;
    private Boolean activeFlag;

    @NotBlank(message = "Please enter the start date of your visa!")
    private Date startDate;

    @NotBlank(message = "Please enter the end date of your visa!")
    private Date endDate;
    private Date lastModifiedDate;
}
