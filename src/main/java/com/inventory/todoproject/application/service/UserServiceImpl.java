package com.inventory.todoproject.application.service;

import com.inventory.todoproject.application.Validator.UserValidator;
import com.inventory.todoproject.application.dto.request.user.ChangePasswordRequest;
import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.request.user.UpdateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.application.ports.in.UserUseCase;
import com.inventory.todoproject.application.ports.out.UserRepositoryPort;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.enums.Roles;
import com.inventory.todoproject.domain.exception.InvalidPasswordException;
import com.inventory.todoproject.domain.exception.UserNotFoundException;
import com.inventory.todoproject.domain.pagination.Page;
import com.inventory.todoproject.domain.pagination.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        userValidator.validateUniqueEmail(request.email() , null);
        userValidator.validateUniqueUsername(request.username() , null);

        final User user = buildUser(request,Roles.CLIENT);
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (request.email() != null &&
                !user.getEmail().equalsIgnoreCase(request.email())) {
            userValidator.validateUniqueEmail(request.email(), userId);
            user.setEmail(request.email());
        }

        if (request.username() != null &&
                !user.getUsername().equalsIgnoreCase(request.username())) {
            userValidator.validateUniqueUsername(request.username(), userId);
            user.setUsername(request.username());
        }

        if (request.name() != null) user.setName(request.name());
        if (request.lastName() != null) user.setLastName(request.lastName());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+ userId ));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        user.changePassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }

    @Override
    public UserResponse createUserWithRole(CreateUserRequest request, Roles roles) {
        userValidator.validateUniqueEmail(request.email() , null);
        userValidator.validateUniqueUsername(request.username() , null);
        User user = buildUser(request, roles);
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsersPaginated(PageRequest pageRequest) {
        Page<User> userPage = userRepository.findAll(pageRequest);

        List<UserResponse> responses =  userPage.getContent()
                .stream().map(this::mapToResponse).toList();

        return new Page<>(
                responses,
                userPage.getPageNumber(),
                userPage.getPageSize(),
                userPage.getTotalElements()
        );
    }

    private User buildUser(CreateUserRequest request, Roles role){
        User user = new User();
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole(role);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }


    private UserResponse mapToResponse(User user){
        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getLastName(),
                user.getEmail()
        );
        return response;
    }
}
