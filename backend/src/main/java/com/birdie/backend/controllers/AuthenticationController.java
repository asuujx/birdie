package com.birdie.backend.controllers;

import com.birdie.backend.dto.response.JwtAuthenticationResponse;
import com.birdie.backend.dto.request.LoginRequest;
import com.birdie.backend.dto.request.RefreshTokenRequest;
import com.birdie.backend.dto.request.RegisterRequest;
import com.birdie.backend.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody LoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/refresh")
    public JwtAuthenticationResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.refresh(refreshTokenRequest);
    }
}
