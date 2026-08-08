package com.hospital.patientservice.service.impl;

import com.hospital.patientservice.dto.PatientRequest;
import com.hospital.patientservice.dto.PatientResponse;
import com.hospital.patientservice.entity.Patient;
import com.hospital.patientservice.repository.PatientRepository;
import com.hospital.patientservice.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponse createPatient(PatientRequest request) {

        Patient patient = Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .bloodGroup(request.getBloodGroup())
                .address(request.getAddress())
                .build();

        Patient savedPatient = patientRepository.save(patient);

        return PatientResponse.builder()
                .id(savedPatient.getId())
                .firstName(savedPatient.getFirstName())
                .lastName(savedPatient.getLastName())
                .email(savedPatient.getEmail())
                .phone(savedPatient.getPhone())
                .dateOfBirth(savedPatient.getDateOfBirth())
                .gender(savedPatient.getGender())
                .bloodGroup(savedPatient.getBloodGroup())
                .address(savedPatient.getAddress())
                .build();
    }

    @Override
    public List<PatientResponse> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(patient -> PatientResponse.builder()
                        .id(patient.getId())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .email(patient.getEmail())
                        .phone(patient.getPhone())
                        .dateOfBirth(patient.getDateOfBirth())
                        .gender(patient.getGender())
                        .bloodGroup(patient.getBloodGroup())
                        .address(patient.getAddress())
                        .build())
                .toList();
    }

    @Override
    public PatientResponse getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .address(patient.getAddress())
                .build();
    }

    @Override
    public PatientResponse updatePatient(Long id, PatientRequest request) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setAddress(request.getAddress());

        Patient updatedPatient = patientRepository.save(patient);

        return PatientResponse.builder()
                .id(updatedPatient.getId())
                .firstName(updatedPatient.getFirstName())
                .lastName(updatedPatient.getLastName())
                .email(updatedPatient.getEmail())
                .phone(updatedPatient.getPhone())
                .dateOfBirth(updatedPatient.getDateOfBirth())
                .gender(updatedPatient.getGender())
                .bloodGroup(updatedPatient.getBloodGroup())
                .address(updatedPatient.getAddress())
                .build();
    }
}