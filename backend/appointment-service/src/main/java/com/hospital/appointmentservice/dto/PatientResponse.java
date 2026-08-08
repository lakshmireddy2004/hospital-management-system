package com.hospital.appointmentservice.dto;

import lombok.Data;

@Data
public class PatientResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String gender;
}