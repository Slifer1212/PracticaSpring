package com.inventory.todoproject.infrastructure.adapters.out.persistence.repositories;

import com.inventory.todoproject.application.ports.out.TaskRepositoryPort;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.domain.pagination.Page;
import com.inventory.todoproject.domain.pagination.PageRequest;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.TaskEntity;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.jpa.JpaTaskRepository;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Task> findAllByUserId(Long userId, PageRequest pageRequest) {
      return null;
    }
    @Override
    public Page<Task> findByUserIdAndState(Long userId, TaskState state, PageRequest pageRequest) {
        return null;
    }

    private org.springframework.data.domain.PageRequest convertToSpringPageRequest(
            PageRequest pageRequest) {

        if (pageRequest.getSortBy() != null && !pageRequest.getSortBy().isEmpty()) {
            Sort.Direction direction = pageRequest.getDirection().equalsIgnoreCase("DESC")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            return org.springframework.data.domain.PageRequest.of(
                    pageRequest.getPage(),
                    pageRequest.getSize(),
                    Sort.by(direction, pageRequest.getSortBy())
            );
        }

        return org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize()
        );
    }
}
