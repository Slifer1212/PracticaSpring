package com.inventory.todoproject.infraestructure.jparepository;

import com.inventory.todoproject.infraestructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional <UserEntity> findByEmail(String email);
    Optional <UserEntity> findByNameAndLastName (String name, String LastName);
}
