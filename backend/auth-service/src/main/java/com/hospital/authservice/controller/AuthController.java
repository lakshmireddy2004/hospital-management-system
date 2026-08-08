package com.hospital.authservice.controller;

import com.hospital.authservice.dto.AuthResponse;
import com.hospital.authservice.dto.LoginRequest;
import com.hospital.authservice.dto.RegisterRequest;
import com.hospital.authservice.dto.UserResponse;
import com.hospital.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "Auth Service is Working";
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Welcome! JWT Authentication Successful.");
    }
}