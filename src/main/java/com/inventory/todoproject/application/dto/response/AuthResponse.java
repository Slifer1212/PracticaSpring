package com.inventory.todoproject.application.dto.response;

import java.util.Set;

public record AuthResponse(
        String jwt,
        Long Id,
        String username,
        String password,
        Set<String> roles
) {
}
