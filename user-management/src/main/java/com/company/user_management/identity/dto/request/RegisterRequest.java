package com.company.user_management.identity.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String username;

    private String email;

    private String password;
}
