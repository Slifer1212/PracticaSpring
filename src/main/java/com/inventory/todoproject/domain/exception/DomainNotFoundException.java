package com.inventory.todoproject.domain.exception;

public abstract class DomainNotFoundException extends RuntimeException {
    protected DomainNotFoundException(String message) {
        super(message);
    }
}
