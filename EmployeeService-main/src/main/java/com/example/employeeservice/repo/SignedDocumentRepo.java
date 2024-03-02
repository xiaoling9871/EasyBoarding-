package com.example.employeeservice.repo;

import com.example.employeeservice.entity.SignedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignedDocumentRepo extends MongoRepository<SignedDocument, String> {
}
