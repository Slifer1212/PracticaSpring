package com.inventory.todoproject.application.dto.response;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskState state;
    private LocalDate dueDate;
    private UserResponse user;

    public static TaskResponse toDomain(Task task) {
        if (task == null) return null;

        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setState(task.getState());
        response.setDueDate(task.getDueDate());
        response.setUser(UserResponse.fromDomain(task.getUser()));

        return response;
    }
}
