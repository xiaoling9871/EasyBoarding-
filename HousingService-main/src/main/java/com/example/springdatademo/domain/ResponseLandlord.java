package com.example.springdatademo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLandlord {
    String fullname;
    String phone;
    String email;
}
