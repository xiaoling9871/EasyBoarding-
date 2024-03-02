package com.example.springdatademo.repository;

import com.example.springdatademo.domain.Facility;
import com.example.springdatademo.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface FacilityRepo extends JpaRepository<Facility, Integer> {
    Optional<Facility> findFacilityById(Integer id);
    Facility findFacilityByHouseIdAndType(Integer id, String type);
    List<Facility> findFacilitiesByHouseId(Integer id);

    List<Facility> findAll();
    @Transactional
    void deleteAllByHouse(House house);

    @Query(value="select f.type, f.quantity from facility f where house_id = ?",nativeQuery = true)
    List<Map<String,Object>> facilitySummary(Integer house_id);

}
