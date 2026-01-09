package com.company.user_management.identity.controller;

import com.company.user_management.common.constants.Constants;
import com.company.user_management.identity.dto.request.AssignRoleRequest;
import com.company.user_management.identity.dto.request.LoginRequest;
import com.company.user_management.identity.dto.request.RegisterRequest;
import com.company.user_management.common.response.ApiResponse;
import com.company.user_management.identity.dto.response.AuthResponse;
import com.company.user_management.identity.dto.response.UserProfileResponse;
import com.company.user_management.identity.service.AuthService;
import com.company.user_management.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("User registered successfully")
                        .data(null)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .status(Constants.STATUS_SUCCESS)
                .message("Login Successfull")
                .data(response)
                .build()
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getCurrentUser() {

        UserProfileResponse response = userService.getCurrentUser();
        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponse>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("User profile fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse<Void>> assignRoles(@PathVariable Long userId, @RequestBody AssignRoleRequest request) {

        userService.assignRoles(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Role assigned to user")
                        .data(null)
                        .build());
    }

}
