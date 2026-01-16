package com.inventory.todoproject.infrastructure.adapters.out.persistence.jpa;

import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional <UserEntity> findByEmail(String email);
    Optional <UserEntity> findByUsername(String userName);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);

}
