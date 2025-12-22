package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.task.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.task.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.domain.exception.SearchCriteria;
import com.inventory.todoproject.domain.exception.TaskNotFoundException;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import com.inventory.todoproject.domain.repositories.TaskRepository;
import com.inventory.todoproject.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest taskRequest) {
        Objects.requireNonNull(taskRequest, "taskRequest must not be null");

        Long userId = taskRequest.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(new SearchCriteria("id", userId)));

        Task task = buildTask(taskRequest, user);
        Task savedTask = taskRepository.save(task);
        return TaskResponse.toDomain(savedTask);
    }

    private Task buildTask(CreateTaskRequest request, User user) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setState(TaskState.PENDING);
        task.setCreationDate(LocalDate.now());
        task.setDueDate(request.getDueDate());
        task.setUser(user);
        return task;
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

        if (taskRequest.getUserId() != null) {
            Long userId = taskRequest.getUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(new SearchCriteria("id", id)));
            task.setUser(user);
        }

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
