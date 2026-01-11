package com.inventory.todoproject.infraestructure.jparepository;

import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infraestructure.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRespository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByState(TaskState state);
}
