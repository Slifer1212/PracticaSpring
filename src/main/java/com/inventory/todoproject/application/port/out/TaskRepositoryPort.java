package com.inventory.todoproject.application.port.out;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;

import java.util.List;

public interface TaskRepositoryPort extends BaseRepository<Task> {
    List<Task> findByState(TaskState state);
}
