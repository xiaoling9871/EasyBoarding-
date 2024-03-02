package com.example.employeeservice.service;

import com.example.employeeservice.domain.request.EmployeeDto;
import com.example.employeeservice.domain.request.EmployeePatchDto;
import com.example.employeeservice.domain.response.EmployeeSummaryDto;
import com.example.employeeservice.entity.DriverLicense;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.entity.PersonalDocument;
import com.example.employeeservice.exception.IdNotFoundException;
import com.example.employeeservice.repo.*;
import com.example.employeeservice.service.remote.RemoteS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Value("${s3.file-path-prefix}")
    private String filePathPrefix;
    private final EmployeeRepo employeeRepo;
    private final VisaStatusRepo visaStatusRepo;
    private final AddressRepo addressRepo;
    private final DriverLicenseRepo driverLicenseRepo;
    private final ContactRepo contactRepo;
    private final RemoteS3Service s3Service;

    private final PersonalDocumentRepo personalDocumentRepo;

    public EmployeeService(EmployeeRepo employeeRepo, VisaStatusRepo visaStatusRepo, AddressRepo addressRepo, DriverLicenseRepo driverLicenseRepo, ContactRepo contactRepo, RemoteS3Service s3Service,
                           PersonalDocumentRepo personalDocumentRepo) {
        this.s3Service = s3Service;
        this.addressRepo = addressRepo;
        this.contactRepo = contactRepo;
        this.employeeRepo = employeeRepo;
        this.visaStatusRepo = visaStatusRepo;
        this.driverLicenseRepo = driverLicenseRepo;
        this.personalDocumentRepo = personalDocumentRepo;
    }

    public Employee createEmployee(Optional<MultipartFile> drivingLicense,
                                   Optional<MultipartFile> optReceipt,
                                   Employee employee) {
        // save driver license
        drivingLicense.ifPresent(f -> {
            employee.getDriverLicense().setFilePath(uploadFileToS3(f).getPath());
            driverLicenseRepo.save(employee.getDriverLicense());
        });
        // save opt receipt
        employee.setPersonalDocumentList(new ArrayList<>());
        optReceipt.ifPresent(f -> employee.getPersonalDocumentList().add(uploadFileToS3(f)));
        // save address list
        addressRepo.saveAll(employee.getAddressList());
        // save visa status list
        visaStatusRepo.saveAll(employee.getVisaStatusList().stream()
                .peek(v -> v.setLastModifiedDate(new Date()))
                .collect(Collectors.toList()));
        // save contact list
        contactRepo.saveAll(employee.getContactList());
        System.out.println(employee);
        return employeeRepo.save(employee);
    }

    public List<EmployeeSummaryDto> findEmployeeByHouseId(Integer houseId) {
        System.out.println("You are looking for employees in house: " + houseId);
        return employeeRepo.findAllByHouseId(houseId).stream()
                .map(emp -> EmployeeSummaryDto.builder()
                        .email(emp.getEmail())
                        .phoneNumber(emp.getCellPhone())
                        .fullName(emp.getFirstName() + " " + emp.getLastName())
                        .build())
                .collect(Collectors.toList());
    }

    public String getFullNameByEmployeeId(String employeeId) {
        Optional<Employee> possibleEmp = employeeRepo.findById(employeeId);
        possibleEmp.orElseThrow(IdNotFoundException::new);
        Employee employee = possibleEmp.get();
        return employee.getFirstName() + " " + employee.getLastName();
    }

    public Employee updateEmployee(Optional<MultipartFile> drivingLicense,
                                   Optional<MultipartFile> optReceipt,
                                   String employeeId,
                                   EmployeePatchDto changedFields) {
        Optional<Employee> possibleEmployee = employeeRepo.findById(employeeId);
        possibleEmployee.orElseThrow(IdNotFoundException::new);
        Employee employee = possibleEmployee.get();
        // update driver license
        drivingLicense.ifPresent(f -> {
            employee.getDriverLicense().setFilePath(uploadFileToS3(f).getPath());
            employee.setDriverLicense(driverLicenseRepo.save(employee.getDriverLicense()));
        });
        // update opt receipt
        optReceipt.ifPresent(f -> employee.getPersonalDocumentList().set(0, uploadFileToS3(f)));
        // update address list
        if (changedFields.getAddressList() != null)
            employee.setAddressList(addressRepo.saveAll(changedFields.getAddressList()));
        // update visa status list
        if (changedFields.getVisaStatusList() != null)
            employee.setVisaStatusList(visaStatusRepo.saveAll(changedFields.getVisaStatusList().stream()
                    .peek(v -> v.setLastModifiedDate(new Date()))
                    .collect(Collectors.toList())));
        // update contact list
        if (changedFields.getContactList() != null)
            employee.setContactList(contactRepo.saveAll(employee.getContactList()));
        // update first name
        if (changedFields.getDOB() != null) employee.setDOB(changedFields.getDOB());
        if (changedFields.getSSN() != null) employee.setFirstName(changedFields.getSSN());
        if (changedFields.getLastName() != null) employee.setLastName(changedFields.getLastName());
        if (changedFields.getCellPhone() != null) employee.setFirstName(changedFields.getCellPhone());
        if (changedFields.getFirstName() != null) employee.setFirstName(changedFields.getFirstName());
        if (changedFields.getGender() != null) employee.setGender(changedFields.getGender());
        if (changedFields.getHouseId() != null) employee.setHouseId(changedFields.getHouseId());
        if (changedFields.getDriverLicenseNumber() != null) {
            employee.getDriverLicense().setLicenseNumber(changedFields.getDriverLicenseNumber());
            driverLicenseRepo.save(employee.getDriverLicense());
        }
        if (changedFields.getDriverLicenseExpiration() != null) {
            employee.getDriverLicense().setExpirationDate(changedFields.getDriverLicenseExpiration());
            driverLicenseRepo.save(employee.getDriverLicense());
        }
        return employeeRepo.save(employee);
    }

    private PersonalDocument uploadFileToS3(MultipartFile file) {
        String filePath = filePathPrefix + s3Service.uploadFile(file);
        PersonalDocument document = PersonalDocument.builder()
                .createDate(new Date())
                .path(filePath)
                .build();
        return personalDocumentRepo.save(document);
    }

    public Employee saveEmployeeJson(EmployeeDto employeeDto) {
        Employee employee = new Employee(employeeDto);
        // 1. save all the personal documents
        personalDocumentRepo.saveAll(employee.getPersonalDocumentList());
//        employee.setPersonalDocumentList(employeeDto.getPersonalDocumentList());
        // 2. save all the contacts
        contactRepo.saveAll(employee.getContactList());
        // 3. save all the address status
        addressRepo.saveAll(employee.getAddressList());
        // 4. save all the visa status
        employee.getVisaStatusList().forEach(visaStatus -> visaStatus.setLastModifiedDate(new Date()));
        visaStatusRepo.saveAll(employee.getVisaStatusList());
        // 5. save driver license
        employee.setDriverLicense(DriverLicense.builder()
                .expirationDate(employeeDto.getDriverLicenseExpiration())
                .licenseNumber(employeeDto.getDriverLicenseNumber())
                .filePath(employeeDto.getDriverLicenseFilePath())
                .build());
        driverLicenseRepo.save(employee.getDriverLicense());
        // 6. save employee
        employeeRepo.save(employee);
        return  employee;
    }


    public Optional<Employee> findEmployeeById(String employeeId) {
        return employeeRepo.findById(employeeId);
    }

    public void saveEmployee(Employee employee) {
        employeeRepo.save(employee);
    }
}
