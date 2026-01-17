package com.inventory.todoproject.infrastructure.adapters.in.rest;

import com.inventory.todoproject.application.dto.request.user.ChangePasswordRequest;
import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.request.user.UpdateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.application.ports.in.UserUseCase;
import com.inventory.todoproject.infrastructure.security.jwt.JwtAuthenticationDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserUseCase userUseCase;

    @Autowired
    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        UserResponse response = userUseCase.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody  CreateUserRequest request){
        UserResponse response = userUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@Valid @RequestBody UpdateUserRequest request, Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        UserResponse response  = userUseCase.updateUser(userId , request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                               Authentication authentication)
    {
       Long userId = getUserIdFromAuthentication(authentication);
       userUseCase.changePassword(userId, request);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser(Authentication authentication){
        Long userId = getUserIdFromAuthentication(authentication);
        userUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    private Long getUserIdFromAuthentication (Authentication authentication){
        if (authentication.getDetails() instanceof JwtAuthenticationDetails) {
            return ((JwtAuthenticationDetails) authentication.getDetails()).getUserId();
        }
        throw new IllegalStateException("Authentication details do not contain userId");
    }
}
