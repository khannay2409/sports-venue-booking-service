package com.company.user_management.controller;

import com.company.user_management.dto.request.AssignRoleRequest;
import com.company.user_management.dto.request.LoginRequest;
import com.company.user_management.dto.request.RegisterRequest;
import com.company.user_management.dto.response.AuthResponse;
import com.company.user_management.dto.response.UserProfileResponse;
import com.company.user_management.service.AuthService;
import com.company.user_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserProfileResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/roles")
    public void assignRoles(
            @PathVariable Long userId,
            @RequestBody AssignRoleRequest request) {
        userService.assignRoles(userId, request);
    }

}
