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
public class EmployeeDto {
    private String id;
    private Integer userId;
    @NotBlank(message = "Please enter your first name!")
    private String firstName;
    @NotBlank(message = "Please enter your last name!")
    private String lastName;
    @NotBlank(message = "Please enter your email!")
    private String email;
    @NotBlank(message = "Please enter your cell phone")
    private String cellPhone;
    private String gender;
    @NotBlank(message = "Please enter your SSN!")
    private String SSN;
    @NotNull(message = "Please enter your birthday!")
    private Date DOB;

    @NotBlank(message = "Please enter your diver license!")
    private String driverLicenseNumber;

    @NotNull(message = "Please enter the expiration date of your diver license!")
    private Date driverLicenseExpiration;

    private String driverLicenseFilePath;
    private Integer houseId;

    @NotEmpty(message = "Please enter your contact!")
    private List<Contact> contactList;

    @NotEmpty(message = "Please enter your address!")
    private List<Address> addressList;

    @NotEmpty(message = "Please enter your visa status!")
    private List<VisaStatus> visaStatusList;

    private List<PersonalDocument> personalDocumentList;

    private List<SignedDocument> signedDocumentList;

}
