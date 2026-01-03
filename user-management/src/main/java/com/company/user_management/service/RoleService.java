package com.company.user_management.service;

import com.company.user_management.dto.request.CreateRoleRequest;
import com.company.user_management.entity.Role;
import com.company.user_management.exceptions.BadRequestException;
import com.company.user_management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void createRole(CreateRoleRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Role name cannot be empty");
        }

        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException("Role already exists");
        }

        roleRepository.save(Role.builder().name(request.getName()).build());
    }
}
