package com.hospital.patientservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private String gender;

    private String bloodGroup;

    private String address;
}