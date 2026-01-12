package com.inventory.todoproject.infraestructure.adapters.out.persistence.mapper;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.infraestructure.adapters.out.persistence.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final UserMapper userMapper;

    public TaskMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TaskEntity toEntity(Task entity){
        if(entity == null) return null;

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(entity.getId());
        taskEntity.setTitle(entity.getTitle());
        taskEntity.setDescription(entity.getDescription());
        taskEntity.setState(entity.getState());
        taskEntity.setDueDate(entity.getDueDate());
        taskEntity.setCreationDate(entity.getCreationDate());
        taskEntity.setUser(userMapper.toEntity(entity.getUser()));
        return taskEntity;
    }

    public Task toDomain(TaskEntity entity){
        if(entity == null) return null;

        Task task = new Task();

        task.setId(entity.getId());
        task.setTitle(entity.getTitle());
        task.setDescription(entity.getDescription());
        task.setState(entity.getState());
        task.setCreationDate(entity.getCreationDate());
        task.setDueDate(entity.getDueDate());
        task.setId(entity.getId());
        task.setUser(userMapper.toDomain(entity.getUser()));
        return task;
    }
}
