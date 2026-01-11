package com.inventory.todoproject.presentation.controller;

import com.inventory.todoproject.application.dto.request.task.CreateTaskRequest;
import com.inventory.todoproject.application.dto.request.task.UpdateTaskRequest;
import com.inventory.todoproject.application.dto.response.TaskResponse;
import com.inventory.todoproject.application.service.TaskService;
import com.inventory.todoproject.domain.enums.TaskState;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public List<TaskResponse> getAll(){
        return taskService.findAll();
    }

    @PostMapping()
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest request){
        return taskService.create(request);
    }

    @GetMapping("/{id}")
    public TaskResponse getOne(@PathVariable Long id){
        return taskService.findOne(id);
    }

    @GetMapping("/state/{state}")
    public List<TaskResponse> getByState(@PathVariable TaskState state){
        return taskService.findByState(state);
    }

    @PutMapping("/{id}")
    public TaskResponse update (@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest updateTaskRequest){
        return taskService.update(id, updateTaskRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }
}
