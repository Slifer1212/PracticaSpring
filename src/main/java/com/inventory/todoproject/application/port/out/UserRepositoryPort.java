package com.inventory.todoproject.application.port.out;

import com.inventory.todoproject.domain.entities.User;

import java.util.Optional;

public interface UserRepositoryPort extends BaseRepository<User> {
    Optional<User> findByEmail(String email);
    Optional <User> findByUserName(String userName);
}
