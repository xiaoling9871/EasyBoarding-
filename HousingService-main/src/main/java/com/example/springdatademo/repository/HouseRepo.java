package com.example.springdatademo.repository;

import com.example.springdatademo.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface HouseRepo extends JpaRepository<House, Integer> {

    Optional<House> findById(Integer id);
    House findByAddress(String address);

    Optional<House> findAllByLandlordId(Integer id);

    @Query(value="select h.id, h.max_occupant from house h",nativeQuery = true)
    List<Map<String,Object>> houseOccList();


}
