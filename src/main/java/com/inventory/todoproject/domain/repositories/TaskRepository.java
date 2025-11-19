package com.inventory.todoproject.domain.repositories;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.entities.TaskState;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById (Long id);
    List<Task> findAll();
    void delete (Long id);
    boolean existsById(Long id);
    List<Task> findByState(TaskState state);
}
