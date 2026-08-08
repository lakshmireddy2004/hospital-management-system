package com.hospital.patientservice.service;

import com.hospital.patientservice.dto.PatientRequest;
import com.hospital.patientservice.dto.PatientResponse;

import java.util.List;

public interface PatientService {

    PatientResponse createPatient(PatientRequest request);

    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(Long id);

    PatientResponse updatePatient(Long id, PatientRequest request);
}