package com.example.authserver.domain.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class RegisterUserDto {
    @NotBlank(message = "username is required")
    private String username;

    @Size(min = 5, message = "password cannot be shorter than 5 digits")
    private String password;

    @Email
    @NotBlank(message = "Email is required")
    private String email;
}
