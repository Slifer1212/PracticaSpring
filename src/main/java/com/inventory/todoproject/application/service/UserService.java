package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.Validator.UserValidator;
import com.inventory.todoproject.application.dto.request.user.ChangePasswordRequest;
import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.request.user.UpdateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.exception.InvalidPasswordException;
import com.inventory.todoproject.domain.exception.SearchCriteria;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import com.inventory.todoproject.domain.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request){

        userValidator.validateUniqueEmail(request.email(), null);
        userValidator.validateUniqueUsername(request.username(), null);
        User user = toEntity(request);
        userRepository.save(user);
        return UserResponse.fromDomain(user);
    }


    @Transactional
    public UserResponse update(Long id, UpdateUserRequest userRequest){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                new SearchCriteria("Id", id )));

        if (userRequest.email() != null &&
                !user.getEmail().equalsIgnoreCase(userRequest.email())) {
            userValidator.validateUniqueEmail(userRequest.email(), id);
            user.setEmail(userRequest.email());
        }

        if (userRequest.username() != null &&
                !user.getUsername().equalsIgnoreCase(userRequest.username())) {
            userValidator.validateUniqueUsername(userRequest.username(), id);
            user.setUsername(userRequest.username());
        }

        if (userRequest.name() != null) user.setName(userRequest.name());
        if (userRequest.lastName() != null) user.setLastName(userRequest.lastName());

        return UserResponse.fromDomain(user);
    }
    public List<UserResponse> findAll(){
        return userRepository.findAll().stream().map
                (UserResponse::fromDomain).collect(Collectors.toList());
    }

    public UserResponse findByUserName(String userName){
        return userRepository.findByUserName(userName).map(UserResponse::fromDomain)
                .orElseThrow(() -> new UserNotFoundException(new SearchCriteria("Username" ,
                        userName)));
    }

    public UserResponse findByEmail(String email){
        return userRepository.findByEmail(email).map(UserResponse::fromDomain)
                .orElseThrow(() -> new UserNotFoundException(new SearchCriteria("User email" , email)));
    }

    @Transactional
    public void delete (Long id){
        userRepository.delete(id);
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                new SearchCriteria("User id", id)
                        )
                );

        if (!passwordEncoder.matches(
                request.currentPassword(),
                user.getPassword()
        )) {
            throw new InvalidPasswordException();
        }

        String encodedPassword = passwordEncoder.encode(request.newPassword());
        user.changePassword(encodedPassword);
        userRepository.save(user);
    }

    private User toEntity( CreateUserRequest request){
        User user = new User();
        user.setUsername(request.username());
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.roles());
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(true);

        return user;
    }
}
