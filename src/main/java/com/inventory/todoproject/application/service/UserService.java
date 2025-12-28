package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.request.user.UpdateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.exception.EmailAlreadyExistsException;
import com.inventory.todoproject.domain.exception.SearchCriteria;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
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
        userRepository.save(user);
        return UserResponse.fromDomain(user);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest userRequest){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                new SearchCriteria("Id", id )));

        if(userRequest.email() != null && !user.getEmail().equalsIgnoreCase(userRequest.email())){

            userRepository.findByEmail(userRequest.email()).ifPresent(existingUser ->
            {
                if(!existingUser.getId().equals(id)){
                    throw new EmailAlreadyExistsException(userRequest.email());
                }
            });

            user.setEmail(userRequest.email());
        }

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

    private User toEntity( CreateUserRequest request){
        User user = new User();
        user.setUsername(request.username());
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(BCrypt.hashpw(request.password() ,  BCrypt.gensalt()));
        user.setRole(request.role());
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(true);

        return user;
    }
}
