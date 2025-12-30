package com.inventory.todoproject.application.dto.request.task;

import com.inventory.todoproject.domain.enums.TaskState;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateTaskRequest (

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be a positive number")
     Long userId,

    @Size(max = 200, message = "Title must not exceed 200 characters")
    @NotBlank(message = "Titles is required")
    String title,

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @NotBlank(message = "Description is required")
    String description,

    TaskState state,

    @NotNull(message = "The due date is required")
    @FutureOrPresent(message = "Due date must be in the future")
    LocalDate dueDate
){}
