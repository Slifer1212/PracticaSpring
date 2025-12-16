package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse create (CreateUserRequest userRequest){
        Objects.requireNonNull(userRequest, "userRequest must not be null");

        User user = toEntity(userRequest);
        User savedTask = userRepository.save(user);
        return UserResponse.fromDomain(savedTask);
    }

    public List<UserResponse> findAll(){
        return userRepository.findAll().stream().map
                (UserResponse::fromDomain).collect(Collectors.toList());
    }

    private User toEntity( CreateUserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword() ,  BCrypt.gensalt()));
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(true);

        return user;
    }
}
