package com.example.mongodb.controller;

import com.example.mongodb.entity.EmployeeEntity.Employee;
import com.example.mongodb.repository.EmployeeRepo;
import com.example.mongodb.service.CompositeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/EC")
public class EmployeeController {

    private final CompositeService compositeService;
    private final EmployeeRepo employeeRepo;

    public EmployeeController(CompositeService compositeService,EmployeeRepo employeeRepo){
        this.compositeService = compositeService;
        this.employeeRepo = employeeRepo;
    }
    @PostMapping("/uploadDoc")
    public void UploadDoc(@RequestPart MultipartFile doc, @RequestParam String id){
        compositeService.UploadDoc(doc, id);
        compositeService.updateCurrStatus(id);
    }

    //Use token to identity
//    @GetMapping("/checkstatus")
//    public void checkStatus(){
//        compositeService.checkStatus();
    // get most lastest comment
//
//    }
}
