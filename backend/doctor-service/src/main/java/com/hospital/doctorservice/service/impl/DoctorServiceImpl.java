package com.hospital.doctorservice.service.impl;

import com.hospital.doctorservice.dto.DoctorRequest;
import com.hospital.doctorservice.dto.DoctorResponse;
import com.hospital.doctorservice.entity.Doctor;
import com.hospital.doctorservice.repository.DoctorRepository;
import com.hospital.doctorservice.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {

        Doctor doctor = Doctor.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .experience(request.getExperience())
                .qualification(request.getQualification())
                .department(request.getDepartment())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);

        return DoctorResponse.builder()
                .id(savedDoctor.getId())
                .firstName(savedDoctor.getFirstName())
                .lastName(savedDoctor.getLastName())
                .email(savedDoctor.getEmail())
                .phone(savedDoctor.getPhone())
                .specialization(savedDoctor.getSpecialization())
                .experience(savedDoctor.getExperience())
                .qualification(savedDoctor.getQualification())
                .department(savedDoctor.getDepartment())
                .build();
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(doctor -> DoctorResponse.builder()
                        .id(doctor.getId())
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .email(doctor.getEmail())
                        .phone(doctor.getPhone())
                        .specialization(doctor.getSpecialization())
                        .experience(doctor.getExperience())
                        .qualification(doctor.getQualification())
                        .department(doctor.getDepartment())
                        .build())
                .toList();
    }

    @Override
    public DoctorResponse getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return DoctorResponse.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .email(doctor.getEmail())
                .phone(doctor.getPhone())
                .specialization(doctor.getSpecialization())
                .experience(doctor.getExperience())
                .qualification(doctor.getQualification())
                .department(doctor.getDepartment())
                .build();
    }

    @Override
    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setExperience(request.getExperience());
        doctor.setQualification(request.getQualification());
        doctor.setDepartment(request.getDepartment());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return DoctorResponse.builder()
                .id(updatedDoctor.getId())
                .firstName(updatedDoctor.getFirstName())
                .lastName(updatedDoctor.getLastName())
                .email(updatedDoctor.getEmail())
                .phone(updatedDoctor.getPhone())
                .specialization(updatedDoctor.getSpecialization())
                .experience(updatedDoctor.getExperience())
                .qualification(updatedDoctor.getQualification())
                .department(updatedDoctor.getDepartment())
                .build();
    }
}