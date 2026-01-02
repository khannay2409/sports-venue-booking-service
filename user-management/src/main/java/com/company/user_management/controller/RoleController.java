package com.company.user_management.controller;

import com.company.user_management.dto.request.CreateRoleRequest;
import com.company.user_management.entity.Role;
import com.company.user_management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void createRole(@RequestBody CreateRoleRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("Role name cannot be empty");
        }

        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Role already exists");
        }

        roleRepository.save(Role.builder().name(request.getName()).build());
    }
}
