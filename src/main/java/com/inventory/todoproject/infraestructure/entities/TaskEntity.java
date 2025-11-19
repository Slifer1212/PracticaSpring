package com.inventory.todoproject.infraestructure.entities;

import com.inventory.todoproject.domain.entities.TaskState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskState state;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

}
