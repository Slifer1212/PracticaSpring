package com.inventory.todoproject.infraestructure.jparepository;

import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infraestructure.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTaskRespository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByState(TaskState state);
}
