package com.hospital.appointmentservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AppointmentResponse {

    private Long id;

    private Long patientId;

    private Long doctorId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String status;

    private String reason;
}