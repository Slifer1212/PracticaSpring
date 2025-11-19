package com.inventory.todoproject.presentation;

import com.inventory.todoproject.application.Dto.Request.CreateTaskRequest;
import com.inventory.todoproject.application.Dto.Request.UpdateTaskRequest;
import com.inventory.todoproject.application.Dto.Response.TaskResponse;
import com.inventory.todoproject.application.Service.TaskService;
import com.inventory.todoproject.domain.entities.TaskState;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public List<TaskResponse> getAll(){
        return taskService.findAll();
    }

    @PostMapping("/task")
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest request){
        return taskService.create(request);
    }

    @GetMapping("/task/id/{id}")
    public Optional<TaskResponse> getOne(@PathVariable Long id){
        return taskService.findOne(id);
    }

    @GetMapping("/task/state/{state}")
    public List<TaskResponse> getByState(@PathVariable TaskState state){
        return taskService.findByState(state);
    }

    @PutMapping("/task/update/{id}")
    public TaskResponse update (@PathVariable Long id, @RequestBody UpdateTaskRequest updateTaskRequest){
        return taskService.update(id, updateTaskRequest);
    }

    @DeleteMapping("/task/delete/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }
}
