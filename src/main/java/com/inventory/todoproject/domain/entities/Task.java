package com.inventory.todoproject.domain.entities;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String title;
    private String description;
    private TaskState state;
    private LocalDate creationDate;
    private LocalDate dueDate;

    public Task(String title, String description, TaskState state,
                LocalDate creationDate, LocalDate dueDate) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}