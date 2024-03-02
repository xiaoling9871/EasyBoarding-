package com.example.mongodb.service.remote;

import com.example.mongodb.entity.EmployeeEntity.Employee;
import com.example.mongodb.entity.EmployeeEntity.PersonalDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@FeignClient("employee-service")
public interface RemoteEmployeeService {


//    @PatchMapping("/{employeeId}")
//    Employee updateEmployee(@RequestPart("driver-license") Optional<MultipartFile> driverLicense,
//                                   @RequestPart("opt-receipt") Optional<MultipartFile>  optReceipt,
//                                   @RequestPart("data") @Validated EmployeePatchDto changedFields,
//                                   @PathVariable String employeeId);

    @GetMapping("/employee/{id}")
    Employee getById(@PathVariable String id);

}
