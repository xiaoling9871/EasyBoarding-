package com.bfs.springdatademo.domain;

import lombok.*;
@Getter
@Setter
@Builder
public class EmailDetail {
    private String recipient;
    private String msgBody;
    private String subject;

}
