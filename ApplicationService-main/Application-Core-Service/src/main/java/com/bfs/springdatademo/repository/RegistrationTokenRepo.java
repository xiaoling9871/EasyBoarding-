package com.bfs.springdatademo.repository;

import com.bfs.springdatademo.domain.RegistrationToken;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepo extends JpaRepository<RegistrationToken, Integer> {


}
