package com.example.mongodb.entity.EmployeeEntity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document
@Data
@Builder
public class PersonalDocument {
    @Id
    private String id;

    private String path;

    private String comment;

    private Date createDate;
}


