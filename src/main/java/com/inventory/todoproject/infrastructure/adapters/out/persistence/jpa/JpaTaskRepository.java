// infrastructure/adapters/out/persistence/jpa/JpaTaskRepository.java
package com.inventory.todoproject.infrastructure.adapters.out.persistence.jpa;

import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByUserId(Long userId);

    List<TaskEntity> findByUserIdAndState(Long userId, TaskState state);

    boolean existsByIdAndUserId(Long id, Long userId);
}