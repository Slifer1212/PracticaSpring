package com.inventory.todoproject.application.dto.request.user;

import com.inventory.todoproject.domain.enums.Roles;

import java.util.Set;

public class UpdateUserRequest {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Set<Roles> role;
    private boolean enabled;
}
