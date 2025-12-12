package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.domain.exception.TaskNotFoundException;
import com.inventory.todoproject.domain.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setState(TaskState.PENDING);
        task.setCreationDate(LocalDate.now());
        task.setDueDate(taskRequest.getDueDate());

        Task savedTask = taskRepository.save(task);

        return TaskResponse.toDomain(savedTask);
    }

    public List<TaskResponse> findAll(){
        return taskRepository.findAll().stream().map
                (TaskResponse::toDomain).collect(Collectors.toList());
    }

    public TaskResponse findOne(Long id){
        return taskRepository.findById(id)
                .map(TaskResponse::toDomain)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public TaskResponse update(Long id, UpdateTaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (taskRequest.getTitle() != null) {
            task.setTitle(taskRequest.getTitle());
        }
        if (taskRequest.getDescription() != null) {
            task.setDescription(taskRequest.getDescription());
        }
        if (taskRequest.getState() != null) {
            task.setState(taskRequest.getState());
        }
        if (taskRequest.getDueDate() != null) {
            task.setDueDate(taskRequest.getDueDate());
        }

        Task updatedTask = taskRepository.save(task);
        return TaskResponse.toDomain(updatedTask);
    }

    public void delete(Long id){
        taskRepository.delete(id);
    }

    public List<TaskResponse>findByState(TaskState state){
        return taskRepository.findByState(state).stream().
                map(TaskResponse::toDomain).collect(Collectors.toList());
    }
}
