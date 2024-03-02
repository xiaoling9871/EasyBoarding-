package com.bfs.springdatademo.domain;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ApplicationWorkFlow")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationWorkFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "employeeid")
    private String employeeID;

    @Column(name = "createdate")
    @CreatedDate
    private Date createDate;

    @Column(name = "lastmodificationdate")
    @CreatedDate
    private Date lastmodificationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;
}