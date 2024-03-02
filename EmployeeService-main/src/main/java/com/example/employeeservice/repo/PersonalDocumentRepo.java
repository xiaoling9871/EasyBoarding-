package com.example.employeeservice.repo;

import com.example.employeeservice.entity.PersonalDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalDocumentRepo extends MongoRepository<PersonalDocument, String> {
}
