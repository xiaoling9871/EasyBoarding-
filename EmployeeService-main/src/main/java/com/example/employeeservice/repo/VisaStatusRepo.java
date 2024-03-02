package com.example.employeeservice.repo;

import com.example.employeeservice.entity.VisaStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisaStatusRepo extends MongoRepository<VisaStatus, String> {
}
