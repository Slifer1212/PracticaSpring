package com.inventory.todoproject.infraestructure.mapper;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.infraestructure.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskEntity toEntity(Task entity){
        if(entity == null) return null;

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(entity.getId());
        taskEntity.setTitle(entity.getTitle());
        taskEntity.setDescription(entity.getDescription());
        taskEntity.setState(entity.getState());
        taskEntity.setDueDate(entity.getDueDate());
        taskEntity.setCreationDate(entity.getCreationDate());

        return taskEntity;
    }

    public Task toDomain(TaskEntity entity){
        if(entity == null) return null;

        Task task = new Task();
        task.setTitle(entity.getTitle());
        task.setDescription(entity.getDescription());
        task.setState(entity.getState());
        task.setCreationDate(entity.getCreationDate());
        task.setDueDate(entity.getDueDate());
        task.setId(entity.getId());

        return task;
    }
}
