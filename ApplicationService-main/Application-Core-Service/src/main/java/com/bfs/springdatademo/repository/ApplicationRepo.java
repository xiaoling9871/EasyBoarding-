package com.bfs.springdatademo.repository;

import com.bfs.springdatademo.domain.ApplicationWorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<ApplicationWorkFlow, Integer>{
    Optional<ApplicationWorkFlow> findByEmployeeID(String id);

    Optional<List<ApplicationWorkFlow>> findApplicationWorkFlowByStatus(String status);

    Optional<ApplicationWorkFlow> findApplicationWorkFlowByEmployeeID(String userid);
}
