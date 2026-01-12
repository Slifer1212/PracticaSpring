package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.task.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.task.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.application.port.out.TaskRepositoryPort;
import com.inventory.todoproject.application.port.out.UserRepositoryPort;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.domain.exception.SearchCriteria;
import com.inventory.todoproject.domain.exception.TaskNotFoundException;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl {
    private final TaskRepositoryPort taskRepository;
    private final UserRepositoryPort userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepositoryPort taskRepository, UserRepositoryPort userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest taskRequest) {
        Objects.requireNonNull(taskRequest, "taskRequest must not be null");

        Long userId = taskRequest.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(new SearchCriteria("id", userId)));

        final Task task = buildTask(taskRequest, user);
        Task savedTask = taskRepository.save(task);
        return TaskResponse.toDomain(savedTask);
    }
    @Transactional
    public TaskResponse update(Long id, UpdateTaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        Long userId = taskRequest.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(new SearchCriteria("id", id)));

        task.setUser(user);
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setState(taskRequest.state());
        task.setDueDate(taskRequest.dueDate());

        taskRepository.save(task);
        return TaskResponse.toDomain(task);
    }

    private Task buildTask(CreateTaskRequest request, User user) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setState(TaskState.PENDING);
        task.setCreationDate(LocalDate.now());
        task.setDueDate(request.dueDate());
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
    public void delete(Long id){
        taskRepository.delete(id);
    }

    public List<TaskResponse>findByState(TaskState state){
        return taskRepository.findByState(state).stream().
                map(TaskResponse::toDomain).collect(Collectors.toList());
    }
}
