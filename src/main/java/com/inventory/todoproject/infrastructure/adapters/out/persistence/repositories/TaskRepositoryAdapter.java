package com.inventory.todoproject.infrastructure.adapters.out.persistence.repositories;

import com.inventory.todoproject.application.ports.out.TaskRepositoryPort;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.TaskEntity;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.jpa.JpaTaskRepository;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository jpaTaskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskRepositoryAdapter(JpaTaskRepository jpaTaskRepository, TaskMapper taskMapper) {
        this.jpaTaskRepository = jpaTaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = taskMapper.toEntity(task);
        TaskEntity savedEntity = jpaTaskRepository.save(entity);
        return taskMapper.toDomain(savedEntity);
     }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id)
                .map(taskMapper::toDomain);
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return jpaTaskRepository.findAllByUserId(userId)
                .stream().map(taskMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Task> findByUserIdAndState(Long userId, TaskState state) {
        return jpaTaskRepository.findByUserIdAndState(userId, state)
                .stream()
                .map(taskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaTaskRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaTaskRepository.existsById(id);
    }
}
