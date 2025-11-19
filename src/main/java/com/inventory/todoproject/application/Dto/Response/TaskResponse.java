package com.inventory.todoproject.application.Dto.Response;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.entities.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskState state;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;

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
