package com.bfs.springdatademo.domain;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registrationToken")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String Email;

    @Column(name = "expirationdate")
    @CreatedDate
    private Date date;

    @Column(name = "createby")
    private int createBy;
}