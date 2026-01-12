package com.inventory.todoproject.infraestructure.adapters.out.persistence.repositories;

import com.inventory.todoproject.application.port.out.TaskRepositoryPort;
import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.enums.TaskState;
import com.inventory.todoproject.infraestructure.adapters.out.persistence.entities.TaskEntity;
import com.inventory.todoproject.infraestructure.adapters.out.persistence.jpa.JpaTaskRepository;
import com.inventory.todoproject.infraestructure.adapters.out.persistence.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskMapper mapper;
    private final JpaTaskRepository jpaRepository;

    public TaskRepositoryAdapter(TaskMapper mapper, JpaTaskRepository jpaRespository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRespository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        TaskEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }


    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Task> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Task> findByState(TaskState state) {
        return jpaRepository.findByState(state).stream().map(mapper ::toDomain).toList();
    }
}
