package com.hospital.doctorservice.service;

import com.hospital.doctorservice.dto.DoctorRequest;
import com.hospital.doctorservice.dto.DoctorResponse;

import java.util.List;

public interface DoctorService {

    DoctorResponse createDoctor(DoctorRequest request);

    List<DoctorResponse> getAllDoctors();

    DoctorResponse getDoctorById(Long id);

    DoctorResponse updateDoctor(Long id, DoctorRequest request);
}