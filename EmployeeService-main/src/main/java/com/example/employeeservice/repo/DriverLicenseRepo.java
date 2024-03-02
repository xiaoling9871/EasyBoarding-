package com.example.employeeservice.repo;

import com.example.employeeservice.entity.DriverLicense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverLicenseRepo extends MongoRepository<DriverLicense, String> {
}
