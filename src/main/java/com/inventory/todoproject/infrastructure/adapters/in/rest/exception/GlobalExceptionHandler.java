package com.inventory.todoproject.infrastructure.adapters.in.rest.exception;

import com.inventory.todoproject.domain.exception.DomainException;
import com.inventory.todoproject.domain.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String , String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error
        -> errors.put(error.getField() , error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainNotFound(
            DomainException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleAlreadyExistEmail(
            EmailAlreadyExistsException existsException)
    {
        Map <String, String> errors = new HashMap<>();
        errors.put("message" , existsException.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}
