package com.company.user_management.identity.controller;

import com.company.user_management.common.constants.Constants;
import com.company.user_management.identity.dto.request.CreateRoleRequest;
import com.company.user_management.common.response.ApiResponse;
import com.company.user_management.identity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createRole(@RequestBody CreateRoleRequest request) {

        roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Void>builder()
                        .status(Constants.STATUS_SUCCESS)
                        .message("Role created successfully")
                        .data(null)
                        .build());
    }
}
