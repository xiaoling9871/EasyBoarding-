package com.example.employeeservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Document
@Data
@Builder
public class Address {
    @Id
    private String id;

    @NotBlank(message = "Please enter the address line1!")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "Please enter the city name of your address!")
    private String city;

    @NotBlank(message = "Please enter the state name of your address!")
    private String state;

    @NotBlank(message = "Please enter the zip code of your address!")
    private String zipCode;
}
