package com.example.employeeservice.repo;

import com.example.employeeservice.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends MongoRepository<Employee, String> {

    List<Employee> findAllByHouseId(Integer houseId);

    Optional<Employee> findByUserId(Integer userId);
}
