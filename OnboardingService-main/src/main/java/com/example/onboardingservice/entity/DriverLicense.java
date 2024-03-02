package com.example.onboardingservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
public class DriverLicense {
    @Id
    private String id;

    private String licenseNumber;

    private String filePath;

    private Date expirationDate;
}
