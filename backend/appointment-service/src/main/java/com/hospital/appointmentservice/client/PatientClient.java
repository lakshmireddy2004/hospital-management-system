package com.hospital.appointmentservice.client;

import com.hospital.appointmentservice.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("/patients/{id}")
    PatientResponse getPatientById(@PathVariable Long id);
}