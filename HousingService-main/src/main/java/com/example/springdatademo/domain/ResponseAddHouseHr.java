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
public class ResponseAddHouseHr {

    private String address;
    private Integer maxOccupants;
    private String firstname;
    private String lastname;
    private String email;
    private String cellphone;
    private String type;
    private String description;
    private int quantity;


}

