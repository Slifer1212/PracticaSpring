package com.inventory.todoproject.domain.repositories;

import com.inventory.todoproject.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User>{
    Optional<User> findByEmail(String email);
    Optional <User> findByUserName(String userName);
}
