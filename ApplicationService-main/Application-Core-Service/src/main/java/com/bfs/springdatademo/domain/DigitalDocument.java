package com.bfs.springdatademo.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "digitaldocument")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "isrequired")
    private int isrequired;

    @Column(name = "path")
    private String path;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;
}
