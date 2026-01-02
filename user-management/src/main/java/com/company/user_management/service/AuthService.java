package com.company.user_management.service;

import com.company.user_management.Gateways.UserEventProducer;
import com.company.user_management.dto.request.LoginRequest;
import com.company.user_management.dto.request.RegisterRequest;
import com.company.user_management.dto.response.AuthResponse;
import com.company.user_management.entity.Role;
import com.company.user_management.entity.User;
import com.company.user_management.repository.RoleRepository;
import com.company.user_management.repository.UserRepository;
import com.company.user_management.security.JwtTokenProvider;
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

        if (request == null
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Invalid registration request");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

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

        if (request == null
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Invalid login request");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
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
}
