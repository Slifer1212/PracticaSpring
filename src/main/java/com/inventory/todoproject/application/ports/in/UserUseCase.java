package com.inventory.todoproject.application.ports.in;

import com.inventory.todoproject.application.dto.request.user.ChangePasswordRequest;
import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.request.user.UpdateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.domain.enums.Roles;
import com.inventory.todoproject.domain.pagination.Page;
import com.inventory.todoproject.domain.pagination.PageRequest;

import java.util.List;

public interface UserUseCase {
    UserResponse createUser (CreateUserRequest request);
    UserResponse updateUser (Long userId, UpdateUserRequest request);
    void deleteUser(Long userId);
    UserResponse getUserById (Long userId);
    List<UserResponse> getAllUsers();
    void changePassword(Long userId, ChangePasswordRequest request);
    UserResponse createUserWithRole(CreateUserRequest request, Roles roles);
    Page<UserResponse> getAllUsersPaginated(PageRequest pageRequest);
}
