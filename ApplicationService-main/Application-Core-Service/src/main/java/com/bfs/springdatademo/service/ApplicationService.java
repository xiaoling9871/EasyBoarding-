package com.bfs.springdatademo.service;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.bfs.springdatademo.domain.ApplicationWorkFlow;
import com.bfs.springdatademo.domain.DigitalDocument;
import com.bfs.springdatademo.repository.ApplicationRepo;
import com.bfs.springdatademo.repository.DigitalDocumentRepo;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepo applicationRepo;

    private final DigitalDocumentRepo digitalDocumentRepo;

    public ApplicationService(ApplicationRepo applicationRepo, DigitalDocumentRepo digitalDocumentRepo){
        this.applicationRepo = applicationRepo;
        this.digitalDocumentRepo = digitalDocumentRepo;
    }
    public String checkStatus(String id){
        return applicationRepo.findByEmployeeID(id).get().getStatus();
    }

    public void updatestatus(String id, String updated, String comment){
        Optional<ApplicationWorkFlow> applicationWorkFlow  = applicationRepo.findByEmployeeID(id);
        applicationWorkFlow.get().setLastmodificationDate(new Date());
        applicationWorkFlow.get().setStatus(updated);
        applicationWorkFlow.get().setComment(comment);
        applicationRepo.save(applicationWorkFlow.get());

        //function to send to message to Raddit
    }


    public List<ApplicationWorkFlow> findByStatus(String status){
        return applicationRepo.findApplicationWorkFlowByStatus(status).get();

    }

    public List<DigitalDocument> listDigitalDocument(){
        return digitalDocumentRepo.findAll();
    }

    public Optional<ApplicationWorkFlow> findCommentbyId(String userid){
        return applicationRepo.findApplicationWorkFlowByEmployeeID(userid);
    }

}
