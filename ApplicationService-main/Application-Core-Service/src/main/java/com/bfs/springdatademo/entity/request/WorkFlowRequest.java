package com.bfs.springdatademo.entity.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class WorkFlowRequest {

    private String employeeid;
    private Date createDate;
    private Date lastmodificationdate;
    private String status;
    private String comment;


}