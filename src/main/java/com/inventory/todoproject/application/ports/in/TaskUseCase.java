package com.inventory.todoproject.application.ports.in;

import com.inventory.todoproject.application.dto.request.task.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.task.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.domain.pagination.Page;
import com.inventory.todoproject.domain.pagination.PageRequest;

import java.util.List;

public interface TaskUseCase {
    TaskResponse createTask(CreateTaskRequest request, Long userId);
    TaskResponse updateTask(Long taskId, UpdateTaskRequest request, Long userId);
    void deleteTask(Long taskId, Long userId);
    TaskResponse getTaskById(Long taskId, Long userId);
    List<TaskResponse> getAllTasksByUser(Long userId);
    List<TaskResponse> getTasksByState(TaskState state, Long userId);
    Page<TaskResponse> getAllTasksByUserPaginated(Long userId, PageRequest pageRequest);
    Page<TaskResponse> getTasksByStatePaginated(TaskState state, Long userId, PageRequest pageRequest);
}
