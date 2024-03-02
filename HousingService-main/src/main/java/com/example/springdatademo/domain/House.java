package com.example.springdatademo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "house")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Landlord_ID")
    private Landlord landlord;

    @Column(name = "Address")
    private String address;

    @Column(name = "Max_Occupant")
    private Integer maxOccupant;
    @OneToMany(mappedBy = "house", cascade = CascadeType.PERSIST)
    private List<Facility> facilities;

}
