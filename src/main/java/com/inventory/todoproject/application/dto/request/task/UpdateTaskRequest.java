package com.inventory.todoproject.application.dto.request.task;

import com.inventory.todoproject.domain.enums.TaskState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequest {

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be a positive number")
    private Long userId;

    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private TaskState state;

    private LocalDate dueDate;
}
