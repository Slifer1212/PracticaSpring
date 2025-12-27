 package com.inventory.todoproject.application.dto.request.user;

 import jakarta.validation.constraints.Email;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.Size;


public record UpdateUserRequest(
        @NotNull
        Long id,

        @NotBlank
        @Size(min = 3, max = 50)
        String username,

        @NotBlank
        @Size(min = 1, max = 50)
        String name,

        @NotBlank
        @Size(min = 1, max = 50)
        String lastName,

        @NotBlank
        @Email
        @Size(max = 254)
        String email
) {}
