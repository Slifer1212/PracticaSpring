package com.inventory.todoproject.application.dto.request.auth;

public record LoginRequest(
        String username,
        String password
) {
}
