package com.inventory.todoproject.domain.exception;

public class UserNotFoundException extends DomainNotFoundException{
    public UserNotFoundException(Long id){
        super("User not found with Id : " + id);
    }
}
