package com.hospital.doctorservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String specialization;

    private Integer experience;

    private String qualification;

    private String department;
}