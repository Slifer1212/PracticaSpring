package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.task.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.task.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.application.ports.in.TaskUseCase;
import com.inventory.todoproject.application.ports.out.TaskRepositoryPort;
import com.inventory.todoproject.application.ports.out.UserRepositoryPort;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.domain.exception.TaskNotFoundException;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import com.inventory.todoproject.domain.pagination.Page;
import com.inventory.todoproject.domain.pagination.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskUseCase {
    private final TaskRepositoryPort taskRepository;
    private final UserRepositoryPort userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepositoryPort taskRepository, UserRepositoryPort userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request, Long userId) {
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }

        final Task task = buildTask(request);
        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);

    }

    @Override
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        if(!task.isOwnedBy(userId)){
            throw new IllegalArgumentException("Task does not belong to user");
        }

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setState(request.state());
        task.setDueDate(request.dueDate());
        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.isOwnedBy(userId)) {
            throw new IllegalArgumentException("Task does not belong to user");
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(taskId)
        );

        if(!task.isOwnedBy(userId))
        {
            throw new IllegalArgumentException("Task does not belong to this user");
        }
        return mapToResponse(task);
    }

    @Override
    public List<TaskResponse> getAllTasksByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return taskRepository.findAllByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getTasksByState(TaskState state, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return taskRepository.findByUserIdAndState(userId, state).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasksByUserPaginated(Long userId, PageRequest pageRequest) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        Page<Task> taskPage =  taskRepository.findAllByUserId(userId, pageRequest);

        List<TaskResponse> responses = taskPage.getContent()
                .stream().map(this::mapToResponse)
                .toList();

        return new Page<>(
                responses,
                taskPage.getPageNumber(),
                taskPage.getPageSize(),
                taskPage.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasksByStatePaginated(TaskState state, Long userId, PageRequest pageRequest) {
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }

        Page<Task> taskPage = taskRepository.findByUserIdAndState(userId,state,pageRequest);

        List<TaskResponse> responses = taskPage.getContent()
                .stream().map(this::mapToResponse)
                .toList();

        return new Page<>(
                responses,
                taskPage.getPageNumber(),
                taskPage.getPageSize(),
                taskPage.getTotalElements()
        );
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getState(),
                task.getDueDate(),
                task.getUserId()
        );
        return response;
    }

    private Task buildTask(CreateTaskRequest request){
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setState(TaskState.PENDING);
        task.setCreationDate(LocalDate.now());
        task.setDueDate(request.dueDate());
        task.setUserId(request.userId());
        return task;
    }
}
