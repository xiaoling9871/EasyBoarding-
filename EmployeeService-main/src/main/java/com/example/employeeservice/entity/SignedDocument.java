package com.example.employeeservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
public class SignedDocument {
    @Id
    private String id;

    private String path;

    private Date createDate;
}
