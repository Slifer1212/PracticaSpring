package com.inventory.todoproject.application.dto.response;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;

import java.time.LocalDate;

public record TaskResponse (

     Long id,
     String title,
     String description,
     TaskState state,
     LocalDate dueDate,
     Long userId
)
{
    public static TaskResponse toDomain(Task task) {
        if (task == null) return null;

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getState(),
                task.getDueDate(),
                task.getUserId());
    }
}
