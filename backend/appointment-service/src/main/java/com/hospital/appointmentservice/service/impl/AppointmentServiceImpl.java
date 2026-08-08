package com.hospital.appointmentservice.service.impl;

import com.hospital.appointmentservice.client.DoctorClient;
import com.hospital.appointmentservice.client.PatientClient;
import com.hospital.appointmentservice.dto.AppointmentRequest;
import com.hospital.appointmentservice.dto.AppointmentResponse;
import com.hospital.appointmentservice.entity.Appointment;
import com.hospital.appointmentservice.exception.ServiceUnavailableException;
import com.hospital.appointmentservice.repository.AppointmentRepository;
import com.hospital.appointmentservice.service.AppointmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientClient patientClient,
            DoctorClient doctorClient) {

        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    // =========================================================
    // CREATE APPOINTMENT
    // =========================================================

    @Override
    @CircuitBreaker(
            name = "appointmentService",
            fallbackMethod = "createAppointmentFallback"
    )
    public AppointmentResponse createAppointment(AppointmentRequest request) {

        log.info(
                "Creating appointment for patientId={} and doctorId={}",
                request.getPatientId(),
                request.getDoctorId()
        );

        // Verify Patient exists
        patientClient.getPatientById(request.getPatientId());

        log.info(
                "Patient verification successful for patientId={}",
                request.getPatientId()
        );

        // Verify Doctor exists
        doctorClient.getDoctorById(request.getDoctorId());

        log.info(
                "Doctor verification successful for doctorId={}",
                request.getDoctorId()
        );

        Appointment appointment = Appointment.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .status(request.getStatus())
                .reason(request.getReason())
                .build();

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        log.info(
                "Appointment created successfully with id={}",
                savedAppointment.getId()
        );

        return AppointmentResponse.builder()
                .id(savedAppointment.getId())
                .patientId(savedAppointment.getPatientId())
                .doctorId(savedAppointment.getDoctorId())
                .appointmentDate(savedAppointment.getAppointmentDate())
                .appointmentTime(savedAppointment.getAppointmentTime())
                .status(savedAppointment.getStatus())
                .reason(savedAppointment.getReason())
                .build();
    }

    // =========================================================
    // GET ALL APPOINTMENTS
    // =========================================================

    @Override
    public List<AppointmentResponse> getAllAppointments() {

        log.info("Fetching all appointments");

        List<AppointmentResponse> appointments =
                appointmentRepository.findAll()
                        .stream()
                        .map(appointment -> AppointmentResponse.builder()
                                .id(appointment.getId())
                                .patientId(appointment.getPatientId())
                                .doctorId(appointment.getDoctorId())
                                .appointmentDate(appointment.getAppointmentDate())
                                .appointmentTime(appointment.getAppointmentTime())
                                .status(appointment.getStatus())
                                .reason(appointment.getReason())
                                .build())
                        .toList();

        log.info(
                "Total appointments found={}",
                appointments.size()
        );

        return appointments;
    }

    // =========================================================
    // GET APPOINTMENT BY ID
    // =========================================================

    @Override
    public AppointmentResponse getAppointmentById(Long id) {

        log.info("Fetching appointment with id={}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment not found with id={}",
                            id
                    );

                    return new RuntimeException(
                            "Appointment not found"
                    );
                });

        log.info(
                "Appointment found with id={}",
                id
        );

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .reason(appointment.getReason())
                .build();
    }

    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    @Override
    public AppointmentResponse updateAppointment(
            Long id,
            AppointmentRequest request) {

        log.info(
                "Updating appointment with id={}",
                id
        );

        // Verify Patient exists
        patientClient.getPatientById(request.getPatientId());

        log.info(
                "Patient verification successful for patientId={}",
                request.getPatientId()
        );

        // Verify Doctor exists
        doctorClient.getDoctorById(request.getDoctorId());

        log.info(
                "Doctor verification successful for doctorId={}",
                request.getDoctorId()
        );

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Appointment not found with id={}",
                            id
                    );

                    return new RuntimeException(
                            "Appointment not found"
                    );
                });

        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(request.getStatus());
        appointment.setReason(request.getReason());

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        log.info(
                "Appointment updated successfully with id={}",
                updatedAppointment.getId()
        );

        return AppointmentResponse.builder()
                .id(updatedAppointment.getId())
                .patientId(updatedAppointment.getPatientId())
                .doctorId(updatedAppointment.getDoctorId())
                .appointmentDate(updatedAppointment.getAppointmentDate())
                .appointmentTime(updatedAppointment.getAppointmentTime())
                .status(updatedAppointment.getStatus())
                .reason(updatedAppointment.getReason())
                .build();
    }

    // =========================================================
    // CIRCUIT BREAKER FALLBACK
    // =========================================================

    public AppointmentResponse createAppointmentFallback(
            AppointmentRequest request,
            Throwable throwable) {

        log.error(
                "Failed to create appointment for patientId={} and doctorId={}. Reason: {}",
                request.getPatientId(),
                request.getDoctorId(),
                throwable.getMessage(),
                throwable
        );

        throw new ServiceUnavailableException(
                "Patient or Doctor Service is currently unavailable. Please try again later."
        );
    }
}