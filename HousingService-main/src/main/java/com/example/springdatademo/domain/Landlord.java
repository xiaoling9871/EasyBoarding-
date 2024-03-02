package com.example.springdatademo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "landlord")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Column(name = "Firstname")
    private String firstname;
    @Column(name = "Lastname")
    private String lastname;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "Email")
    private String email;
    @OneToMany(mappedBy = "landlord", cascade = CascadeType.PERSIST)
    private List<House> houses;

}
