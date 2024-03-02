package com.example.employeeservice.controller;

import com.example.employeeservice.domain.request.EmployeeDto;
import com.example.employeeservice.domain.request.EmployeePatchDto;
import com.example.employeeservice.domain.response.EmployeeSummaryDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.entity.PersonalDocument;
import com.example.employeeservice.entity.SignedDocument;
import com.example.employeeservice.exception.IdNotFoundException;
import com.example.employeeservice.repo.EmployeeRepo;
import com.example.employeeservice.repo.SignedDocumentRepo;
import com.example.employeeservice.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {
    private final SignedDocumentRepo signedDocumentRepo;
    private final EmployeeRepo employeeRepo;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, EmployeeRepo employeeRepo,
                              SignedDocumentRepo signedDocumentRepo) {
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
        this.signedDocumentRepo = signedDocumentRepo;
    }

    @GetMapping
    public List<Employee> findAllByPage(@RequestParam int page,
                                        @RequestParam int size) {
        Page<Employee> employeePage = employeeRepo.findAll(PageRequest.of(page, size, Sort.by("lastName")));
        return employeePage.getContent();
    }

    @GetMapping("/all")
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @PostMapping
    public Employee createEmployee(@RequestPart("driver-license") Optional<MultipartFile> driverLicense,
                                   @RequestPart("opt-receipt") Optional<MultipartFile> optReceipt,
                                   @RequestPart("data") @Validated EmployeeDto employeeDto) {
        System.out.println(employeeDto);
        return employeeService.createEmployee(driverLicense, optReceipt, new Employee(employeeDto));
    }

    @GetMapping("/house/{houseId}")
    public List<EmployeeSummaryDto> getEmployeesByHouseId(@PathVariable Integer houseId) {
        return employeeService.findEmployeeByHouseId(houseId);
    }

    @GetMapping("/name/{employeeId}")
    public String getFullNameByEmployeeId(@PathVariable String employeeId) {
        System.out.println("employee id: " + employeeId);
        return employeeService.getFullNameByEmployeeId(employeeId);
    }

    @PostMapping(value = "/json")
    public Employee createEmployeeJson(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployeeJson(employeeDto);
    }

    @PutMapping(value = "/json")
    public Employee updateEmployeeJson(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployeeJson(employeeDto);
    }

    @PatchMapping("/{employeeId}")
    public Employee updateEmployee(@RequestPart("driver-license") Optional<MultipartFile> driverLicense,
                                   @RequestPart("opt-receipt") Optional<MultipartFile> optReceipt,
                                   @RequestPart("data") @Validated EmployeePatchDto changedFields,
                                   @PathVariable String employeeId) {
        return employeeService.updateEmployee(driverLicense, optReceipt, employeeId, changedFields);
    }

    @PostMapping("/signedDocument/{employeeId}")
    public Employee uploadSignedDocuments(@PathVariable String employeeId,
                                          @RequestBody List<String> documentPaths) {
        Optional<Employee> possibleEmployee = employeeService.findEmployeeById(employeeId);
        possibleEmployee.orElseThrow(IdNotFoundException::new);
        List<SignedDocument> signedDocuments = documentPaths.stream()
                .map(path -> SignedDocument.builder()
                        .createDate(new Date())
                        .path(path)
                        .build())
                .collect(Collectors.toList());
        signedDocumentRepo.saveAll(signedDocuments);
        Employee employee = possibleEmployee.get();
        employee.setSignedDocumentList(signedDocuments);
        employeeService.saveEmployee(employee);
        return employee;
    }


    @GetMapping("/user/{userId}")
    public String getEmployeeIdByUserId(@PathVariable Integer userId) {
        return employeeRepo.findByUserId(userId).orElse(new Employee()).getId();
    }


    @GetMapping("{id}")
    public Employee getById(@PathVariable String id) {
        Optional<Employee> possibleEmployee = employeeRepo.findById(id);
        possibleEmployee.orElseThrow(IdNotFoundException::new);
        return possibleEmployee.get();
    }
}
