package com.inventory.todoproject.infrastructure.adapters.out.persistence.mapper;

import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setName(user.getName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());
        entity.setEnabled(user.isEnabled());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setPassword(user.getPassword());

        return entity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setName(entity.getName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setRole(entity.getRole());
        user.setEnabled(entity.isEnabled());
        user.setCreatedAt(entity.getCreatedAt());

        user.setPassword(entity.getPassword());
        return user;
    }
}
