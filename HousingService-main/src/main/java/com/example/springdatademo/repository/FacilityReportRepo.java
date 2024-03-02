package com.example.springdatademo.repository;

import com.example.springdatademo.domain.FacilityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityReportRepo extends JpaRepository<FacilityReport, Integer> {

    Optional<FacilityReport> findFacilityReportById(Integer id);
    List<FacilityReport> findFacilityReportsByFacilityId(Integer id);
    List<FacilityReport> findFacilityReportsByEmployeeId(Integer id);


}
