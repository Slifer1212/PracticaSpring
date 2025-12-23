 package com.inventory.todoproject.application.dto.request.user;

 import com.inventory.todoproject.domain.enums.Roles;
 import jakarta.validation.constraints.*;

 import java.util.Set;

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
        String email,

        @Size(min = 8, max = 100)
        String password,

        @NotNull
        @NotEmpty
        Set<Roles> role,

        boolean enabled
) {}
