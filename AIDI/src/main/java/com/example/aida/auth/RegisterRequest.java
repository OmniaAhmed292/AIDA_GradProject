package com.example.aida.auth;

import com.example.aida.Entities.Address;
import com.example.aida.Entities.BusinessInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String Fname;
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String Lname;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String email;
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min = 6, max = 20)
    private String Password;
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String User_type;
    private String birthdate;
    private String phoneNumber;
    private String Gender;
    private Address address;
    private BusinessInfo businessInfo;



}
