package com.inventory.todoproject.domain.exception;

public class InvalidPasswordException extends DomainNotFoundException{
    public InvalidPasswordException(){
        super("Current password is incorrect");
    }
}
