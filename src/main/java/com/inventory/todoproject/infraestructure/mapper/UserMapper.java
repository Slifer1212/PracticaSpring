package com.inventory.todoproject.infraestructure.mapper;

import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.infraestructure.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user){
        if (user == null) return null;

        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setLastName(user.getLastName());
        userEntity.setRole(user.getRole());
        userEntity.setPassword(user.getPassword());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setEnabled(user.isEnabled());
        return userEntity;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User();

        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setLastName(entity.getLastName());
        user.setRole(entity.getRole());
        user.setPassword(entity.getPassword());
        user.setCreatedAt(entity.getCreatedAt());
        user.setEnabled(entity.isEnabled());
        return user;

    }
}