package com.inventory.todoproject.application.dto.request.user;

import com.inventory.todoproject.domain.enums.Roles;
import com.inventory.todoproject.infraestructure.entities.TaskEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "username must not be blank")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "name must not be blank")
    @Size(max = 50, message = "name must be at most 50 characters")
    private String name;

    @NotBlank(message = "lastName must not be blank")
    @Size(max = 50, message = "lastName must be at most 50 characters")
    private String lastName;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, max = 100, message = "password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "password must contain upper and lower case letters and at least one digit")
    private String password;

    @NotEmpty(message = "role must contain at least one role")
    private List<@NotNull Roles> role;

    @NotNull(message = "tasks must not be null")
    @Valid
    private Set<TaskEntity> tasks;

}
