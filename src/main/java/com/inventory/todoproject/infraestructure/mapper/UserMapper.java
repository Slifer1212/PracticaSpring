package com.inventory.todoproject.infraestructure.mapper;

import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.enums.Roles;
import com.inventory.todoproject.infraestructure.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    public UserEntity toEntity(User user){
        if (user == null) return null;

        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        Set<Roles> roles = new HashSet<>();
        userEntity.setRole(roles);
        userEntity.setLastName(user.getLastName());
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
        Set<Roles> roles = new HashSet<>();
        user.setRole(roles);
        user.setPassword(entity.getPassword());
        user.setCreatedAt(entity.getCreatedAt());
        user.setEnabled(entity.isEnabled());
        return user;

    }
}