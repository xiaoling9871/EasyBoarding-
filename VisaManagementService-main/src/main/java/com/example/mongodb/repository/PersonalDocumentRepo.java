package com.example.mongodb.repository;

import com.example.mongodb.entity.EmployeeEntity.PersonalDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalDocumentRepo extends MongoRepository<PersonalDocument, String> {
}
