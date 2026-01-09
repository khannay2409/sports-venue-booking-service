package com.company.user_management.identity.service;

import com.company.user_management.identity.Gateways.UserEventProducer;
import com.company.user_management.identity.dto.request.LoginRequest;
import com.company.user_management.identity.dto.request.RegisterRequest;
import com.company.user_management.identity.dto.response.AuthResponse;
import com.company.user_management.identity.entity.Role;
import com.company.user_management.identity.entity.User;
import com.company.user_management.common.exceptions.BadRequestException;
import com.company.user_management.common.exceptions.RoleNotFoundException;
import com.company.user_management.identity.repository.RoleRepository;
import com.company.user_management.identity.repository.UserRepository;
import com.company.user_management.identity.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private static final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    private final JwtTokenProvider jwtTokenProvider;

    private final UserEventProducer userEventProducer;

    public void register(RegisterRequest request) {

        validateRegisterRequest(request);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found in DB" + "ROLE_USER"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);

        userEventProducer.publishUserRegistered(
                user.getId(),
                user.getEmail());
    }

    public AuthResponse login(LoginRequest request) {

        validateLoginRequest(request);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        userEventProducer.publishUserLoggedIn(
                user.getId(),
                user.getEmail()
        );

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        String token = jwtTokenProvider.generateToken(user.getUsername(), roles);

        return new AuthResponse(token);
    }

    private void validateLoginRequest(LoginRequest request)
    {
        if (request == null) {
            throw new BadRequestException("Login request cannot be null");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email is required and cannot be blank");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password is required and cannot be blank");
        }
    }

    private void validateRegisterRequest(RegisterRequest request) {

        if (request == null) {
            throw new BadRequestException("Registration request cannot be null");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email is required and cannot be blank");
        }
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new BadRequestException("Username is required and cannot be blank");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password is required and cannot be blank");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
    }
}
