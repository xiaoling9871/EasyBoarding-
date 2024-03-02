package com.example.demo.Domain;


import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EmailDetail {

    private String recipient;
    private String msgBody;
    private String subject;

}
