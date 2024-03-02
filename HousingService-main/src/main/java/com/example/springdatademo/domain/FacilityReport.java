package com.example.springdatademo.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "facility_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Facility_ID")
    private Facility facility;

    @Column(name = "Employee_Id")
    private String employeeId;

    @Column(name = "Title")
    private String title ;

    @Column(name = "Description")
    private String description;

    @Column(name = "Create_Date")
    private Timestamp createDate;

    @Column(name = "Status")
    private String status;

    @OneToMany(mappedBy = "facilityReport", cascade = CascadeType.PERSIST)
    private List<FacilityReportDetail> facilityReportDetails;

}
