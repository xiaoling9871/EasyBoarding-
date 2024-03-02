package com.example.springdatademo.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "facility_report_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FacilityReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Facility_Report_ID")
    private FacilityReport facilityReport;

    @Column(name = "Employee_Id")
    private String employeeId;

    @Column(name = "Last_Modification_Date")
    private Timestamp lastModificationDate;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "Create_Date")
    private Timestamp createDate;
}
