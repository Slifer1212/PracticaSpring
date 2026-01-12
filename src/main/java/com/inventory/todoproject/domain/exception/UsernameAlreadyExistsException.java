package com.inventory.todoproject.domain.exception;

public class UsernameAlreadyExistsException extends DomainException {
    public UsernameAlreadyExistsException(String username){
        super("The username : " + username + " is already in user" );
    }
}
