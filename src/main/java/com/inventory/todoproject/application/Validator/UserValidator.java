package com.inventory.todoproject.application.Validator;

import com.inventory.todoproject.domain.exception.EmailAlreadyExistsException;
import com.inventory.todoproject.domain.exception.UsernameAlreadyExistsException;
import com.inventory.todoproject.domain.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUniqueEmail(String email, Long userId){
        userRepository.findByEmail(email).ifPresent(
                user -> {
                    if(!user.getId().equals(userId)){
                        throw  new EmailAlreadyExistsException(email);
                    }
                }
        );
    }

    public void validateUniqueUsername(String userName, Long userId){
        userRepository.findByUserName(userName).ifPresent(
                user -> {
                    if(!user.getId().equals(userId)){
                        throw  new UsernameAlreadyExistsException(userName);
                    }
                }
        );
    }
}
