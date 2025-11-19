package com.inventory.todoproject.application.Service;

import com.inventory.todoproject.application.Dto.Request.CreateTaskRequest;
import com.inventory.todoproject.application.Dto.Request.UpdateTaskRequest;
import com.inventory.todoproject.application.Dto.Response.TaskResponse;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.entities.TaskState;
import com.inventory.todoproject.domain.exception.TaskNotFoundException;
import com.inventory.todoproject.domain.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setState(TaskState.Pending);
        task.setCreationDate(LocalDateTime.now());
        task.setDueDate(taskRequest.getDueDate());

        Task savedTask = taskRepository.save(task);

        return TaskResponse.toDomain(savedTask);
    }

    public List<TaskResponse> findAll(){
        return taskRepository.findAll().stream().map
                (TaskResponse::toDomain).collect(Collectors.toList());
    }

    public Optional<TaskResponse>findOne(Long id){
        return Optional.ofNullable(taskRepository.findById(id).map(TaskResponse::toDomain).orElseThrow
                (() -> new TaskNotFoundException(id)));
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
