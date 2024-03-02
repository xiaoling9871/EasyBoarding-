package com.bfs.springdatademo.repository;

import com.bfs.springdatademo.domain.DigitalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DigitalDocumentRepo extends JpaRepository<DigitalDocument, Integer> {

}
