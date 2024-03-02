package com.example.employeeservice.entity;

import com.example.employeeservice.domain.request.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    @Id
    private String id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cellPhone;
    private String gender;
    private String SSN;
    private Date DOB;
    private DriverLicense driverLicense;
    private Integer houseId;

    @Field(name = "contact")
    @NotEmpty(message = "Please enter your contact!")
    private List<Contact> contactList;

    @Field(name = "address")
    @NotEmpty(message = "Please enter your address!")
    private List<Address> addressList;

    @Field(name = "visaStatus")
    @NotEmpty(message = "Please enter your visa status!")
    private List<VisaStatus> visaStatusList;

    @Field(name = "personalDocument")
    private List<PersonalDocument> personalDocumentList;

    @Field(name = "signedDocument")
    private List<SignedDocument> signedDocumentList;

    public Employee(EmployeeDto dto) {
        setId(dto.getId());
        setSSN(dto.getSSN());
        setDOB(dto.getDOB());
        setEmail(dto.getEmail());
        setUserId(dto.getUserId());
        setGender(dto.getGender());
        setHouseId(dto.getHouseId());
        setLastName(dto.getLastName());
        setFirstName(dto.getFirstName());
        setCellPhone(dto.getCellPhone());
        setAddressList(dto.getAddressList());
        setContactList(dto.getContactList());
        setVisaStatusList(dto.getVisaStatusList());
        setSignedDocumentList(dto.getSignedDocumentList());
        setPersonalDocumentList(dto.getPersonalDocumentList());
        setDriverLicense(DriverLicense.builder()
                .expirationDate(dto.getDriverLicenseExpiration())
                .licenseNumber(dto.getDriverLicenseNumber())
                .build());

    }
}
