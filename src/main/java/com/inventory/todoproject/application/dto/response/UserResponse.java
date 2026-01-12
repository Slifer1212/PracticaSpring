package com.inventory.todoproject.application.dto.response;

public record UserResponse(
        Long id,
        String username,
        String name,
        String lastName,
        String email

) {
}
