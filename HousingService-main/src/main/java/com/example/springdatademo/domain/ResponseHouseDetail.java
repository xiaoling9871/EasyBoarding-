package com.example.springdatademo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseHouseDetail {
String address;
ResponseLandlord landlord;


}
