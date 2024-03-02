package com.example.employeeservice.domain.request;

import com.example.employeeservice.entity.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class EmployeePatchDto {
    private String id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cellPhone;
    private String gender;
    private String SSN;
    private Date DOB;

    private String driverLicenseNumber;

    private Date driverLicenseExpiration;
    private Integer houseId;

    private List<Contact> contactList;

    private List<Address> addressList;

    private List<VisaStatus> visaStatusList;

    private List<PersonalDocument> personalDocumentList;

    private List<SignedDocument> signedDocumentList;

}
