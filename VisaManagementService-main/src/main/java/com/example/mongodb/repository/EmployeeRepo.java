package com.example.mongodb.repository;

import com.example.mongodb.entity.EmployeeEntity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepo extends MongoRepository<Employee, String> {

    Optional<Employee> findEmployeeById(String id);
}