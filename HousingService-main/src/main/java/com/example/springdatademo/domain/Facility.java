package com.example.springdatademo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "facility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "House_ID")
    private House house;

    @Column(name = "Type")
    private String type;

    @Column(name = "Description")
    private String description ;

    @Column(name = "Quantity")
    private Integer quantity;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.PERSIST)
    private List<FacilityReport> facilityReports;
}
