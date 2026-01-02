package com.inventory.todoproject.domain.repositories;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;

import java.util.List;

public interface TaskRepository extends BaseRepository<Task>{
    List<Task> findByState(TaskState state);
}
