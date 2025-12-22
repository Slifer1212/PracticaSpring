package com.inventory.todoproject.application.dto.request.task;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateTaskRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must not exceed 200 characters")
        String title,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @NotNull(message = "Due date is required")
        @FutureOrPresent(message = "Due date must be in the future")
        LocalDate dueDate,

        @NotNull(message = "User id is required")
        @Positive(message = "User id must be a positive number")
        Long userId
) {}
