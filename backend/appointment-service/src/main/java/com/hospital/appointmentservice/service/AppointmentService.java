package com.hospital.appointmentservice.service;

import com.hospital.appointmentservice.dto.AppointmentRequest;
import com.hospital.appointmentservice.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest request);

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse updateAppointment(Long id, AppointmentRequest request);
}