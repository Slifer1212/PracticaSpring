package com.inventory.todoproject.application.dto.response;

import com.inventory.todoproject.domain.enums.TaskState;

import java.time.LocalDate;

public record TaskResponse (

     Long id,
     String title,
     String description,
     TaskState state,
     LocalDate dueDate,
     Long userId
){}
