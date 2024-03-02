package com.example.springdatademo.repository;

import com.example.springdatademo.domain.FacilityReport;
import com.example.springdatademo.domain.FacilityReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public
interface FacilityReportDetailRepo extends JpaRepository<FacilityReportDetail, Integer> {
    Optional<FacilityReportDetail> findById(Integer id);
    List<FacilityReportDetail> findFacilityReportDetailsByFacilityReport(FacilityReport facilityReport);
    List<FacilityReportDetail> findFacilityReportDetailsByFacilityReportId(Integer id);
    List<FacilityReportDetail> findFacilityReportDetailsByEmployeeId(Integer id);

    FacilityReportDetail findFacilityReportDetailByLastModificationDateAndCreateDate(Timestamp editTime ,Timestamp createTime);

    List<FacilityReportDetail> findAll();

    @Query(value="select d.comment, d.last_modification_date, d.employee_id from facility_report_detail d where d.facility_report_id = ?1",nativeQuery = true)
    List<Map<String, Object>> ResponseReportDetails(Integer id);

    @Query(value="select d.comment, d.last_modification_date,d.create_date, d.employee_id from facility_report_detail d where d.employee_ID = ?1",nativeQuery = true)
    List<Map<String, Object>> ResponseReportDetailsAuthor(String userId);


}