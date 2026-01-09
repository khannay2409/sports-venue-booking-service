package com.company.user_management.identity.service;

import com.company.user_management.identity.dto.request.AssignRoleRequest;
import com.company.user_management.identity.dto.response.UserProfileResponse;
import com.company.user_management.identity.entity.Role;
import com.company.user_management.identity.entity.User;
import com.company.user_management.common.exceptions.RoleNotFoundException;
import com.company.user_management.common.exceptions.UserNotFoundException;
import com.company.user_management.identity.repository.RoleRepository;
import com.company.user_management.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserProfileResponse getCurrentUser() {

        User user = getUser();

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }

    public User getUser()
    {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }


    public void assignRoles(Long userId, AssignRoleRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));

        Set<Role> roles =  request.getRoles()
                .stream()
                .map(roleName ->
                        roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName))
                )
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
    }
}
