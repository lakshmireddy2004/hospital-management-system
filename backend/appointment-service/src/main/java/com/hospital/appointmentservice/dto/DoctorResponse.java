package com.hospital.appointmentservice.dto;

import lombok.Data;

@Data
public class DoctorResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String specialization;

    private String department;
}