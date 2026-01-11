package com.inventory.todoproject.infraestructure.jparepository;

import com.inventory.todoproject.infraestructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional <UserEntity> findByEmail(String email);
    Optional <UserEntity> findByUsername(String userName);
}
