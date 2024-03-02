package com.bfs.springdatademo.service;

import com.bfs.springdatademo.domain.ApplicationWorkFlow;
import com.bfs.springdatademo.repository.ApplicationRepo;
import com.bfs.springdatademo.repository.DigitalDocumentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @InjectMocks
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepo applicationRepo;


    @Test
    public void checkStatusTest(){
        ApplicationWorkFlow applicationWorkFlow = ApplicationWorkFlow.builder().employeeID("123").status("Pending").build();
        Mockito.when(applicationRepo.findByEmployeeID("123")).thenReturn(Optional.of(applicationWorkFlow));
        assertEquals("Pending", applicationService.checkStatus("123"));
    }

    @Test
    public void updatestatusTest(){
        ApplicationWorkFlow applicationWorkFlow = ApplicationWorkFlow.builder().employeeID("123").status("Pending").build();
        Mockito.when(applicationRepo.findByEmployeeID("123")).thenReturn(Optional.of(applicationWorkFlow));
        applicationService.updatestatus("123","A","no");
        Mockito.verify(applicationRepo).save(applicationWorkFlow);
    }

}
