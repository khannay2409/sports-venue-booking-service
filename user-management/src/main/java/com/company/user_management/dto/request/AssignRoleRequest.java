package com.company.user_management.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AssignRoleRequest {

    private Set<String> roles;
}
