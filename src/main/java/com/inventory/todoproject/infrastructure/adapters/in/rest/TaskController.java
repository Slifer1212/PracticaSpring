package com.inventory.todoproject.infrastructure.adapters.in.rest;

import com.inventory.todoproject.application.dto.request.task.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.task.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.application.ports.in.TaskUseCase;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infrastructure.security.jwt.JwtAuthenticationDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskUseCase taskUseCase;

    public TaskController(TaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    /**
     * Crea una nueva tarea
     * POST /api/tasks
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskUseCase.createTask(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una tarea existente
     * PUT /api/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskUseCase.updateTask(id, request, userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una tarea
     * DELETE /api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        taskUseCase.deleteTask(id, userId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una tarea por ID
     * GET /api/tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        TaskResponse response = taskUseCase.getTaskById(id, userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todas las tareas del usuario autenticado
     * GET /api/tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        List<TaskResponse> tasks = taskUseCase.getAllTasksByUser(userId);

        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene tareas filtradas por estado
     * GET /api/tasks/by-state/{state}
     */
    @GetMapping("/by-state/{state}")
    public ResponseEntity<List<TaskResponse>> getTasksByState(
            @PathVariable TaskState state,
            Authentication authentication) {

        Long userId = getUserIdFromAuthentication(authentication);
        List<TaskResponse> tasks = taskUseCase.getTasksByState(state, userId);

        return ResponseEntity.ok(tasks);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication.getDetails() instanceof JwtAuthenticationDetails) {
            return ((JwtAuthenticationDetails) authentication.getDetails()).getUserId();
        }
        throw new IllegalStateException("Authentication details do not contain userId");
    }
}