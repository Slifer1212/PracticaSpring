package com.inventory.todoproject.presentation;

import com.inventory.todoproject.application.dto.request.user.CreateUserRequest;
import com.inventory.todoproject.application.dto.response.UserResponse;
import com.inventory.todoproject.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public UserResponse create(@Valid @RequestBody CreateUserRequest request){
        return userService.create(request);
    }

    @GetMapping()
    public List<UserResponse> getAll(){
        return userService.findAll();
    }

    @GetMapping("/username/{username}")
    public UserResponse getByNameAndLastName(@PathVariable String username){
        return userService.findByUserName(username);
    }

    @GetMapping("/email/{email}")
    public UserResponse getByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }
}
