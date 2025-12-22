package com.inventory.todoproject.domain.exception;


public class UserNotFoundException extends DomainNotFoundException {

    public UserNotFoundException(SearchCriteria criteria) {
        super("User not found with " + criteria.field() + ": " + criteria.value());
    }
}
