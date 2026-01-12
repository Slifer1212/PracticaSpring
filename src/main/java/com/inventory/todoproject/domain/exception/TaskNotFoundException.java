package com.inventory.todoproject.domain.exception;

public class TaskNotFoundException extends DomainException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
