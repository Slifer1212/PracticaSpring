// application/ports/out/TaskRepositoryPort.java
package com.inventory.todoproject.application.ports.out;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Task save(Task task);
    Optional<Task> findById(Long id);
    List<Task> findAllByUserId(Long userId);
    List<Task> findByUserIdAndState(Long userId, TaskState state);
    void deleteById(Long id);
    boolean existsById(Long id);
}