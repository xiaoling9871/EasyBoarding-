package com.bfs.springdatademo.entity.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ResponseApplicationFilter {
    private String employeeid;
    private String status;
}
