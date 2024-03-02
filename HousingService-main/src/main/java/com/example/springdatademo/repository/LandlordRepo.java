package com.example.springdatademo.repository;

import com.example.springdatademo.domain.House;
import com.example.springdatademo.domain.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandlordRepo extends JpaRepository<Landlord, Integer> {

    Landlord findLandlordById(Integer id);


    List<Landlord> findAll();

    Landlord findLandlordByCellphone(String cellphone);

    Landlord findLandlordByHouses(House house);
    Boolean existsByCellphone(String cellphone);
}
