package com.example.mongodb.entity.EmployeeEntity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document
@Data
@Builder
public class Contact {
    @Id
    private String id;

    @NotBlank(message = "Please enter the first name of your contact!")
    private String firstName;

    @NotBlank(message = "Please enter the last name of your contact!")
    private String lastName;

    @NotBlank(message = "Please enter the cellphone of your contact!")
    private String cellPhone;

    @NotBlank(message = "Please enter the email of your contact!")
    @Email(message = "Please check the email format of your contact!")
    private String email;

    @NotBlank(message = "Please enter the relationship of your contact!")
    private String relationship;

    @NotBlank(message = "Please enter the type of your contact!")
    private String type;
}
