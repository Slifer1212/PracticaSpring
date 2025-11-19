package com.inventory.todoproject.domain.entities;
import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String title;
    private String description;
    private TaskState state;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;

    public Task(String title, String description, TaskState state,
                LocalDateTime creationDate, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    public Task(){}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}