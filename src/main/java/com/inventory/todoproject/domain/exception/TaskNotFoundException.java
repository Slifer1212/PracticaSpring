package com.inventory.todoproject.domain.exception;

public class TaskNotFoundException extends DomainNotFoundException {
    public TaskNotFoundException(Long id){
        super("Task not found with Id : " + id);
    }
}
