package com.inventory.todoproject.application.dto.response;

import com.inventory.todoproject.domain.enums.Roles;


public record AuthResponse(
        String token,
        Long userId,
        String username,
        Roles role
) {
}
