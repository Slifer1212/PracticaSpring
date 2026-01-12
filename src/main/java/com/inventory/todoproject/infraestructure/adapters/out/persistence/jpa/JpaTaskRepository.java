package com.inventory.todoproject.infraestructure.adapters.out.persistence.jpa;

import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infraestructure.adapters.out.persistence.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByState(TaskState state);
}
