package com.hospital.authservice.service;

import com.hospital.authservice.dto.AuthResponse;
import com.hospital.authservice.dto.LoginRequest;
import com.hospital.authservice.dto.RegisterRequest;
import com.hospital.authservice.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}