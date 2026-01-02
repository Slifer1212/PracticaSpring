package com.inventory.todoproject.domain.repositories;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    T save(T entity);
    Optional<T> findById (Long id);
    List<T> findAll();
    void delete (Long id);
    boolean existsById(Long id);
}
