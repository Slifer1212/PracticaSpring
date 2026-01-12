package com.inventory.todoproject.application.ports.out;

import com.inventory.todoproject.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save (User user);
    Optional<User> findById (Long userId);
    Optional<User> findByUsername (String userName);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);

}
