package com.inventory.todoproject.application.dto.response;

import com.inventory.todoproject.domain.entities.User;

public record UserResponse(
        Long id,
        String username,
        String name,
        String lastName,
        String email

) {
    public static UserResponse fromDomain(User user) {
        if (user == null) return null;

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
