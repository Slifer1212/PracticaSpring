package com.inventory.todoproject.infraestructure.repositories;

import com.inventory.todoproject.domain.entities.Task;
import com.inventory.todoproject.domain.entities.TaskState;
import com.inventory.todoproject.infraestructure.entities.TaskEntity;
import com.inventory.todoproject.infraestructure.jparepository.JpaTaskRespository;
import com.inventory.todoproject.infraestructure.mapper.TaskMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository implements com.inventory.todoproject.domain.repositories.TaskRepository {

    private final TaskMapper mapper;
    private final JpaTaskRespository jpaRepository;

    public TaskRepository(TaskMapper mapper, JpaTaskRespository jpaRespository) {
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
