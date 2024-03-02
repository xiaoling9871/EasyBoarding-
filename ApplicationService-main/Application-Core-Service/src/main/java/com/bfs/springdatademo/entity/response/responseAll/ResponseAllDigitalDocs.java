package com.bfs.springdatademo.entity.response.responseAll;

import com.bfs.springdatademo.domain.DigitalDocument;
import com.bfs.springdatademo.entity.response.ResponseApplicationFilter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class ResponseAllDigitalDocs {

    private String status;
    private List<String> supportDocs;
}