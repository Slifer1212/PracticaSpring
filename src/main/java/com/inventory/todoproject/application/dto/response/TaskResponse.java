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
    private LocalDate creationDate;
    private LocalDate dueDate;

    public static TaskResponse toDomain(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setState(task.getState());
        response.setCreationDate(task.getCreationDate());
        response.setDueDate(task.getDueDate());
        return response;
    }

}
