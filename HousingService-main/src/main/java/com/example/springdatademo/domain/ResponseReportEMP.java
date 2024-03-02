package com.example.springdatademo.domain;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseReportEMP {
    private Integer id;
    private String fullName;
    private String Title;
    private String Description;
    private String author;
    private Timestamp time;
    private String status;
    private List<Map<String, Object>> facilityReportDetails;

}
