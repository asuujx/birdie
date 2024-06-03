package com.birdie.backend.services;

import com.birdie.backend.dto.response.JwtAuthenticationResponse;
import com.birdie.backend.dto.request.LoginRequest;
import com.birdie.backend.dto.request.RefreshTokenRequest;
import com.birdie.backend.dto.request.RegisterRequest;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Role;
import com.birdie.backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_STUDENT)
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshJwt = jwtService.generateRefreshToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).refreshToken(refreshJwt).build();
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        var refreshJwt = jwtService.generateRefreshToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).refreshToken(refreshJwt).build();
    }

    public JwtAuthenticationResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String userName = jwtService.extractUserName(refreshToken);
        var user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token."));

        if (jwtService.isRefreshTokenValid(refreshToken, user)) {
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).refreshToken(refreshToken).build();
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
