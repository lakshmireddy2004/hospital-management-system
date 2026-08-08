package com.hospital.doctorservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    private Integer experience;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotBlank(message = "Department is required")
    private String department;
}