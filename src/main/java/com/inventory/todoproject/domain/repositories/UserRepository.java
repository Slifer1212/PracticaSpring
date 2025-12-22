package com.inventory.todoproject.domain.repositories;

import com.inventory.todoproject.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById (Long id);
    List<User> findAll();
    void delete (Long id);
    boolean existsById(Long id);
    Optional<User> findByEmail(String email);
    Optional <User> findByUserName(String userName);
}
