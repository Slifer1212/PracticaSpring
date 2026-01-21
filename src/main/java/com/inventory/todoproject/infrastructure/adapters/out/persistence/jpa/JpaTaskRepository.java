package com.inventory.todoproject.infrastructure.adapters.out.persistence.jpa;

import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByUserId(Long userId);

    List<TaskEntity> findByUserIdAndState(Long userId, TaskState state);
    Page<TaskEntity> findAllByUserId(Long userId, Pageable pageable);
    Page<TaskEntity> findByUserIdAndState(Long userId, TaskState state, Pageable pageable);
}